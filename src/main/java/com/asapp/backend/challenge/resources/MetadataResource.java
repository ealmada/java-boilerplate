package com.asapp.backend.challenge.resources;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="metadatas", uniqueConstraints=@UniqueConstraint(columnNames={"id"}))
public class MetadataResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String type; //xml, json, what kind of metadata you are saving

    @Column
    private String metadata; //A converter will use the type and metadata to show this in different ways.

    //You cannot create a metadata without a resource
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="message_id")
    private MessageResource messageResource;


}
