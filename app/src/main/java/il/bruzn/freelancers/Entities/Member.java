package il.bruzn.freelancers.Entities;

import android.graphics.Picture;

import java.util.Observable;
/**
 * Created by Moshe on 20/11/14.
 */

public class Member {

   private String _lastName;
   private String _firstName;
   private String _email;
 //private Location _address;
   private String _phoneNumber;
 //private scpecality
   private Observable<Member> _favorites;
   private Observable<Member> _historic;
   private Picture _picture;
   private String _googlePlus;
   private String _linkedIn;
   private WayToPay _payment;


}
