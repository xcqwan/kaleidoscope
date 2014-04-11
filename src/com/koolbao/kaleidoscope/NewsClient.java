/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.koolbao.kaleidoscope;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class NewsClient {
    private static final String API_URL = "http://shop.koolbao.com/";

    static class NewsLetterResp {
    	int page_avg;
    	int page_count;
    	int page_current;
        List<Letter> tabledata;
        
        @Override
        public String toString() {
        	return "page_size : " + page_avg + ", pages : " + page_count + ", page : " + page_current + ", tabledata : " + tabledata;
        }
    }
    
    static class Letter{
    	String created;
    	String content;
    	
    	@Override
    	public String toString() {
    		return created + content;
    	}
    }

	interface News {
		@FormUrlEncoded
		@POST("/growth/show_growth_list")
		NewsLetterResp letters(@Field("user_id_app") String user_id_app);

		@FormUrlEncoded
		@POST("/growth/show_growth_list")
		void letters(@Field("user_id_app") String user_id_app, Callback<NewsLetterResp> callback);
	}

    public static void getNews(Callback<NewsLetterResp> callback) {
        // Create a very simple REST adapter which points the GitHub API
        // endpoint.
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();

        // Create an instance of our GitHub API interface.
        News news = restAdapter.create(News.class);

        // Fetch and print a list of the contributors to this library.
        news.letters("533b887f6bae0", callback);

    }
}
