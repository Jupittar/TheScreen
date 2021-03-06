package com.github.jupittar.thescreen.screen.movies;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    private MoviesContract.View mView;

    public MoviesModule(MoviesContract.View view) {
        mView = view;
    }

    @Provides
    public MoviesContract.View provideView() {
        return mView;
    }

    @Provides
    @MoviesScope
    public MoviesContract.Presenter<MoviesContract.View> providePresenter(
            MoviesPresenter presenter) {
        presenter.attach(mView);
        return presenter;
    }

    @Provides
    @MoviesScope
    public MoviesContract.Interactor provideInteractor(
            MoviesInteractor interactor) {
        return interactor;
    }

}
