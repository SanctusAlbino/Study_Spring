package smart.notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class NoticeVO {
	private int id, readcnt, no, root, step, indent, rid;
	private String title, content, writer, name, filename, filepath;
	private Date writedate;
}
