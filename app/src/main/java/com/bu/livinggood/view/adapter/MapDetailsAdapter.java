package com.bu.livinggood.view.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bu.livinggood.R;
import com.bu.livinggood.bean.FindPlaceResponse;
import com.bu.livinggood.bean.RatingSet;
import com.bu.livinggood.controller.APIController;
import com.bu.livinggood.databinding.MapDetailsItemBinding;
import com.bu.livinggood.tools.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapDetailsAdapter extends RecyclerView.Adapter<MapDetailsAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View view, int position, Bitmap bitmap);
    }

    private static final String TAG = MapDetailsAdapter.class.getSimpleName();
    private OnItemClickListener listener;
    private List<RatingSet> localDataSet;

    /**
     * Initialize the dataset of the Adapter.
     */
    public MapDetailsAdapter(OnItemClickListener listener) {
        localDataSet = new ArrayList<>();
        this.listener = listener;
    }

    public void setData(List<RatingSet> dataSet) {
        localDataSet = dataSet;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_details_item, parent, false);
        view.findViewById(R.id.cardView).setOnClickListener(view1 -> {
            if (listener != null) {
                int position = (int) view1.getTag();
                listener.onItemClick(view1, position,
                        CacheHelper.sLruCache.get(localDataSet.get(position).getApartment().getAddressLine1()));
            }
        });
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.binding.tvAddress1.setText(localDataSet.get(position).getApartment().getAddressLine1());
        viewHolder.binding.tvAddress2.setText(localDataSet.get(position).getApartment().getAddressLine2());
        viewHolder.binding.outsideRatingBar.setRating((float) localDataSet.get(position).getOverallScore());
        viewHolder.binding.outsideOverallrating.setText(String.valueOf(localDataSet.get(position).getOverallScore()));
        viewHolder.binding.tvBb.setText(Tools.formatBedroomBathroom(
                localDataSet.get(position).getApartment().getBedrooms(),
                localDataSet.get(position).getApartment().getBathrooms()
        ));
        viewHolder.binding.tvPrice.setText(String.format(Locale.US, "$%d/M",
                localDataSet.get(position).getApartment().getPrice()));
        viewHolder.itemView.setTag(position);

        // 1. Get photo from LRUCache
        Bitmap bitmap = CacheHelper.sLruCache.get(localDataSet.get(position).getApartment().getAddressLine1());
        if (bitmap != null) {
            // 2. Photo in cache, then show it directly.
            viewHolder.binding.imgApartment.setImageBitmap(bitmap);
        } else {
            // 3. No photo in cache, request the photo
            apiRequest(viewHolder, position);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    /**
     * Place photo API request
     *
     * @param viewHolder
     * @param position
     */
    private void apiRequest(ViewHolder viewHolder, final int position) {
        // 1. construct APIController
        APIController controller = new APIController();
        // 2. send API request
        controller.requestFindPlace(localDataSet.get(position).getApartment().getRawAddress(),
                // 3. Define callback interface
                new Callback<FindPlaceResponse>() {
                    @Override
                    public void onResponse(Call<FindPlaceResponse> call, Response<FindPlaceResponse> response) {
                        // 4. Get API response data. Get photo reference
                        FindPlaceResponse findPlaceResponse = response.body();
                        if (findPlaceResponse.getCandidates().size() <= 0 ||
                                findPlaceResponse.getCandidates().get(0).getPhotos() == null) {
                            Log.i(TAG, "Find place candidates is empty");
                            return;
                        }

                        // 5. Use photo reference, send another API request to get photo
                        Log.i(TAG, "Find place url: " + response.raw().request());
                        controller.requestPhoto(findPlaceResponse.getCandidates().get(0).getPhotos().get(0).getPhotoReference(),
                                184, 160,
                                // 6. Define callback interface
                                new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Log.i(TAG, "Photo url: " + response.raw().request());
                                        // 7. Get input stream from response, read as bitmap.
                                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                        // 8. Save photo in LRUCache
                                        CacheHelper.sLruCache.put(localDataSet.get(position).getApartment().getAddressLine1(), bitmap);
                                        // 9. Show the photo
                                        viewHolder.binding.imgApartment.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                }
                        );
                    }

                    @Override
                    public void onFailure(Call<FindPlaceResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MapDetailsItemBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = MapDetailsItemBinding.bind(view);
        }
    }

    /**
     * Place Photo cache. LRUCache.
     */
    private static final class CacheHelper {
        private static LruCache<String, Bitmap> sLruCache;

        static {
            sLruCache = new LruCache<String, Bitmap>((int) Runtime.getRuntime().maxMemory() / 4) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount();
                }
            };
        }
    }

}
