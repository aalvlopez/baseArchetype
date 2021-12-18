package com.imatia.taskmanagerFS.apimodel.entity.task;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TaskVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private OffsetDateTime creationDateTime;


    @Column(name="status" )
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserVO owner;

}
