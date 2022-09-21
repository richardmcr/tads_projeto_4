package uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuscaResponse {

    @SerializedName("content")
    @Expose
    private List<ContentResponse> contentResponse = null;

    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;

    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;

    @SerializedName("last")
    @Expose
    private Boolean last;

    @SerializedName("numberOfElements")
    @Expose
    private Integer numberOfElements;

    @SerializedName("first")
    @Expose
    private Boolean first;

    @SerializedName("sort")
    @Expose
    private String sort;

    @SerializedName("size")
    @Expose
    private Integer size;

    @SerializedName("number")
    @Expose
    private Integer number;

    public List<ContentResponse> getContentResponse() {
        return contentResponse;
    }

    public void setContentResponse(List<ContentResponse> contentResponse) {
        this.contentResponse = contentResponse;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
