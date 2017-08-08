package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-10-08 14:16:02
 *
 */
public class MoviesListResult{
   //page number
   public MoviesList MoviesList;

    public com.quark.api.auto.bean.MoviesList getMoviesList() {
        return MoviesList;
    }

    public void setMoviesList(com.quark.api.auto.bean.MoviesList moviesList) {
        MoviesList = moviesList;
    }
}