package br.imd.mybookplace.DTOS;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.Nullable;

public class OfferDTO {
    @JsonProperty("title")
    String title;
    @Nullable
    @JsonProperty("price")
    Double price;
    @Nullable
    @JsonProperty("link")
    String link;
    @Nullable
    @JsonProperty("imageUrl")
    String imageUrl;

    public OfferDTO() {
    }

    public OfferDTO(String title, Double price, String link, String imageUrl) {
        this.title = title;
        this.price = price;
        this.link = link;
        this.imageUrl = imageUrl;
    }


    public String getTitle() {
        return title;
    }
    public Double getPrice() {
        return price;
    }
    public String getLink() {
        return link;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
