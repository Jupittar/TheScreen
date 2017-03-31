package com.github.jupittar.core.movies;

import com.github.jupittar.core.base.UiContract;
import com.github.jupittar.core.data.model.Movie;
import com.github.jupittar.core.data.model.PagingInfo;

import java.util.List;

public interface MoviesUiContract {

    interface View extends UiContract.View {
        void showMovies(List<Movie> movies);

        void addNoMoreMoviesFooter();

        boolean isMoviesEmpty();

        void showErrorLayout();

        void showReloadSnackbar();

        void updatePagingInfo(PagingInfo pagingInfo);

        void clearMovies();
    }

    interface Presenter<V extends View> extends UiContract.Presenter<V> {
        void showMovies(MovieTab tab, int page);
    }

}
