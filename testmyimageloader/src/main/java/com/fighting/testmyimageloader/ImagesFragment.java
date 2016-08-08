package com.fighting.testmyimageloader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.fighting.myimageloader.core.MyImageLoader;

/**
 * 描述：
 * Created by MaJD on 2016/8/8.
 */
public class ImagesFragment extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_fragment, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.id_gridview);
        gridView.setAdapter(new ImageItemAdaper(getActivity(), 0,  MainActivity.imageThumbUrls));
        return view;
    }

    private class ImageItemAdaper extends ArrayAdapter<String> {

        public ImageItemAdaper(Context context, int resource, String[] datas) {
            super(getActivity(), 0, datas);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.image_item_layout, parent, false);
            }
            ImageView imageview = (ImageView) convertView.findViewById(R.id.id_img);
            // 加载图片
            MyImageLoader.getInstance().displayImage(imageview,getItem(position));
            return convertView;
        }

    } // end ImageItemAdaper
}
