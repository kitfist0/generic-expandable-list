package app.sample.adapters.utils;

import android.annotation.SuppressLint;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class GenericExpandableAdapter<T, D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean mExpandedDefault = false;
    private int mItemLayoutId;

    private AsyncListDiffer<T> mListDiffer;
    private SparseBooleanArray mSparseArray;

    protected GenericExpandableAdapter(int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        this.mSparseArray = new SparseBooleanArray();
        DiffUtil.ItemCallback<T> diffUtilCallback = new DiffUtil.ItemCallback<T>() {
            @Override
            public boolean areItemsTheSame(@NonNull T newItem, @NonNull T oldItem) {
                return getItemIdKey(newItem) == getItemIdKey(oldItem);
            }
            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull T newItem, @NonNull T oldItem) {
                return contentsAreSameWhen(newItem, oldItem);
            }
        };
        mListDiffer = new AsyncListDiffer<>(this, diffUtilCallback);
    }

    public abstract int getItemIdKey(T model);

    public abstract boolean contentsAreSameWhen(T modelNew, T modelOld);

    public abstract void onBindData(T model, int position, boolean expanded, D dataBinding);

    public abstract void onItemExpandedOrCollapsed(T model, boolean expanded, D dataBinding);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                mItemLayoutId, parent, false);
        return new ItemViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        onBindData(mListDiffer.getCurrentList().get(position), position,
                mSparseArray.get(getItemIdKey(mListDiffer.getCurrentList().get(position))),
                ((ItemViewHolder) holder).mBinding);
        onItemExpandedOrCollapsed(mListDiffer.getCurrentList().get(position),
                mSparseArray.get(getItemIdKey(mListDiffer.getCurrentList().get(position))),
                ((ItemViewHolder) holder).mBinding);
        ((ViewDataBinding) ((ItemViewHolder) holder).mBinding).getRoot().setOnClickListener(v -> {
            // Expand/collapse item by click
            mSparseArray.put(getItemIdKey(mListDiffer.getCurrentList().get(position)),
                    !mSparseArray.get(getItemIdKey(mListDiffer.getCurrentList().get(position))));
            onItemExpandedOrCollapsed(mListDiffer.getCurrentList().get(position),
                    mSparseArray.get(getItemIdKey(mListDiffer.getCurrentList().get(position))),
                    ((ItemViewHolder) holder).mBinding);
        });
    }

    public void setExpandedDefault(boolean expandedDefault) {
        this.mExpandedDefault = expandedDefault;
    }

    public T getItem(int position) {
        return mListDiffer.getCurrentList().get(position);
    }

    public List<T> getItems() {
        return mListDiffer.getCurrentList();
    }

    @Override
    public int getItemCount() {
        return mListDiffer.getCurrentList().size();
    }

    public void updateItems(List<T> items) {
        appendSparseArray(items);
        mListDiffer.submitList(items);
    }

    private void appendSparseArray(List<T> items) {
        for (T item : items) {
            if (mSparseArray.indexOfKey(getItemIdKey(item)) < 0) {
                mSparseArray.append(getItemIdKey(item), mExpandedDefault);
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        D mBinding;

        ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = (D) binding;
        }
    }
}
