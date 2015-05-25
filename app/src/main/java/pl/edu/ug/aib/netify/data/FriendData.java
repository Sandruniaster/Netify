package pl.edu.ug.aib.netify.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendData extends NodeData implements Serializable{
    public Integer id;
    public String displayName;
    public String firstName;
    public String lastName;
    public String email;
}

