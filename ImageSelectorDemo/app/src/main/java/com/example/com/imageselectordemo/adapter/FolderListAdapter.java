package com.example.com.imageselectordemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.com.imageselectordemo.ImgSelConfig;
import com.example.com.imageselectordemo.R;
import com.example.com.imageselectordemo.bean.Folder;
import com.example.com.imageselectordemo.interfaces.OnFolderChangeListener;

import java.util.List;

/**
 * Created by yuanqingying on 2016/10/10.
 * 底部文件的adapter
 */
public class FolderListAdapter extends EasyLVAdapter<Folder> {

    private Context context;
    private List<Folder> folderList;
    private ImgSelConfig config;

    private int selected = 0;
    private OnFolderChangeListener listener;

    public FolderListAdapter(Context context, List<Folder> folderList, ImgSelConfig config) {
        super(context,folderList, R.layout.item_img_folder);
        this.context = context;
        this.folderList = folderList;
        this.config = config;

    }

    @Override
    public void convert(EasyLVHolder holder, final int position, Folder folder) {
        if (position == 0) {
            holder.setText(R.id.tvFolderName, "所有图片")
                    .setText(R.id.tvImageNum, "共" + getTotalImageSize() + "张");
            ImageView ivFolder = holder.getView(R.id.ivFolder);
            if (folderList.size() > 0) {
                config.loader.displayImage(context, folder.cover.path, ivFolder);
            }
        } else {
            holder.setText(R.id.tvFolderName, folder.name)
                    .setText(R.id.tvImageNum, "共" + folder.images.size() + "张");
            ImageView ivFolder = holder.getView(R.id.ivFolder);
            if (folderList.size() > 0) {
                config.loader.displayImage(context, folder.cover.path, ivFolder);
            }
        }

        if (selected == position) {
            holder.setVisible(R.id.indicator, true);
        } else {
            holder.setVisible(R.id.indicator, false);
        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectIndex(position);
            }
        });
    }

    //设置数据---暂不使用
    public void setData(List<Folder> folders) {
        folderList.clear();
        if (folders != null && folders.size() > 0) {
            folderList.addAll(folders);
        }
        notifyDataSetChanged();
    }

    private int getTotalImageSize() {
        int result = 0;
        if (folderList != null && folderList.size() > 0) {
            for (Folder folder : folderList) {
                result += folder.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int position) {
        if (selected == position)
            return;
        if(listener != null)
            listener.onChange(position, folderList.get(position));
        selected = position;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selected;
    }

    public void setOnFloderChangeListener(OnFolderChangeListener listener) {
        this.listener = listener;
    }
}
