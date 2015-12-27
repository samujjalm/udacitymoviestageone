package com.samujjalm.android.udacitymoviestageone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView imageGrid;
    private ProgressBar progressBar;
    private ArrayList<Movie> gridData;
    private GridViewAdapter mGridAdapter;
    private String FEED_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=72e01e951ea85dfa501bf89e56eedce1";
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        this.imageGrid = (GridView)rootView.findViewById(R.id.gridView);
        this.progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        this.gridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.movie_item_layout, gridData);
        imageGrid.setAdapter(mGridAdapter);



        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                Movie item = (Movie) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), DetailsActivity.class);

                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)


                //Pass the image title and url to DetailsActivity
                intent.putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage()).
                        putExtra("synopsis", item.getPlotSynopsis()).
                        putExtra("release_date", item.getReleaseDate()).
                        putExtra("rating", item.getUserRaing());

                //Start details activity
                startActivity(intent);
            }
        });

        new AsyncHttpTask().execute(FEED_URL);
        progressBar.setVisibility(View.VISIBLE);
        return rootView;
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                URL url = new URL(FEED_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                String response = buffer.toString();
                // 200 represents HTTP OK
                    parseResult(response);
                    result = 1;
            } catch (Exception e) {
                result = 0;
                Log.d("AsyncTask", e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                mGridAdapter.setGridData(gridData);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            progressBar.setVisibility(View.GONE);
        }
    }


    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("results");
            Movie item;
            gridData.clear();
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                item = new Movie();
                item.setTitle(title);
                String URL = "https://image.tmdb.org/t/p/w185";
                URL += post.optString("poster_path");
                item.setImage(URL);
                item.setPlotSynopsis(post.optString("overview"));
                item.setReleaseDate(post.optString("release_date"));
                item.setUserRaing(String.valueOf(post.optDouble("vote_average")));

                gridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
