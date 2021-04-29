package com.laboratory.demo.entry;

import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String no;
    private String name;
    private int isdelete;
    private Date lasttime;
}
