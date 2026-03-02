package dataHomeworkSix;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties("_meta")
public class ResourceList {
    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    private Integer total;
    @JsonProperty("total_pages")
    private Integer totalPages;
    private List<DataResource> data;
    private SupportResource support;

    public ResourceList(){super();}

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<DataResource> getData() {
        return data;
    }

    public void setData(List<DataResource> data) {
        this.data = data;
    }

    public SupportResource getSupport() {
        return support;
    }

    public void setSupport(SupportResource support) {
        this.support = support;
    }

    public ResourceList(Integer page, Integer perPage, Integer total, Integer totalPages, List<DataResource> data, SupportResource support) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.data = data;
        this.support = support;
    }
}
