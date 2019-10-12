package app.sample.adapters.db;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

@Entity
public class User {

    @PrimaryKey
    private int id;
    private String icon;
    private String name;
    private String bday;
    private String city;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.icon = "";
        this.bday = "";
        this.city = "";
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getBday() {
        return bday;
    }
    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @BindingAdapter("itemImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE).circleCrop())
                .into(view);
    }
}
