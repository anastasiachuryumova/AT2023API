package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("_meta")
public class PeopleCreated extends People{

    private String id;
    private String createdAt;

    public PeopleCreated(){super();}

    public PeopleCreated(String name, String job, String id, String createdAt) {
        super(name, job);
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getName(){return super.getName();}
    @Override
    public String getJob(){return super.getJob();}
    @Override
    public void setName(String name){super.setName(name);}
    @Override
    public void setJob(String job){super.setJob(job);}
}
