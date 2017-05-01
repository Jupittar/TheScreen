package com.github.jupittar.thescreen.screen.movies;

import com.github.jupittar.thescreen.data.model.Movie;
import com.github.jupittar.thescreen.data.model.PagingInfo;
import com.github.jupittar.thescreen.screen.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenter
        extends BasePresenter<MoviesContract.View>
        implements MoviesContract.Presenter<MoviesContract.View> {

    private MoviesContract.Interactor mInteractor;

    @Inject
    public MoviesPresenter(MoviesContract.Interactor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void showMovies(MovieTab tab, int page) {
        if (page == PagingInfo.NO_MORE_PAGES) return;

        if (page == 1) {
            getMvpView().hideErrorLayout();
            getMvpView().showLoading();
            getMvpView().clearMovies();
        }

        Disposable disposable = mInteractor
                .getMovies(tab, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> getMvpView().hideLoading())
                .subscribe(moviesWrapper -> {
                    List<Movie> movies = moviesWrapper.getMovies();
                    PagingInfo pagingInfo = moviesWrapper.getPagingInfo();
                    getMvpView().updatePagingInfo(pagingInfo);

                    if (pagingInfo.getCurrentPage() == 1) getMvpView().addLoadingFooter();

                    if (pagingInfo.isLastPage()) getMvpView().showNoMoreMoviesFooter();

                    getMvpView().showMovies(movies);
                }, throwable -> {
                    if (isNetworkException(throwable)) {
                        if (getMvpView().isMoviesEmpty()) {
                            getMvpView().showErrorLayout();
                        } else if (page > 1) {
                            getMvpView().showReloadSnackbar();
                        }
                    } else {
                        getMvpView().showErrorMessage(throwable);
                    }
                    throwable.printStackTrace();
                });
        addDisposable(disposable);
    }
}
