package com.imatia.taskmanagerFS.apimodel.entity.task;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import javax.persistence.Entity;
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
import org.apache.catalina.User;

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

    @ManyToOne
    @JoinColumn(name = "username")
    private UserVO owner;

}
