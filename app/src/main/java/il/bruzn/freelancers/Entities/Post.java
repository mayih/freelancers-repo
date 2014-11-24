package il.bruzn.freelancers.Entities;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Moshe on 20/11/14.
 */
public class Post {
    private Member _author;
    private Member _taker;
    private String _title;
    private Date _date;
    private String _speciality;
    private ArrayList<String> _keyword;
    private Opinion _opinion;

}
