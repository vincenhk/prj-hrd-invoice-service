package com.ad1.invoice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String parent_id;
	private int menu_level;
	private String menu_name;
	private String path_content;
	private int sort_child;
	private String menu_id;
	private String is_active;
	private String keterangan;
	private Date create_date;
	private String path_icon;
	private String path_faq;

}
