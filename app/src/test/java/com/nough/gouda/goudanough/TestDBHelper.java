package com.nough.gouda.goudanough;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.nough.gouda.goudanough.beans.Address;
import com.nough.gouda.goudanough.beans.Comment;
import com.nough.gouda.goudanough.beans.Restaurant;
import com.nough.gouda.goudanough.beans.User;
import com.nough.gouda.goudanough.databases.DBHelper;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Railanderson Sena on 2016-11-25.
 */

public class TestDBHelper extends Activity {

    private static DBHelper dbh;
    public static final String TAG = "ActMain";// for logging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = DBHelper.getDBHelper(this);
    }

    @Test
    public void testInsertNewResto(){

        //int a = (int) dbh.insertNewResto("test1","99.99","*****","chinese food",1,8500,8500,new byte[]{},"hello test");
        //assertEquals(1,a);
    }

    private void testInserts(){
        int b = (int)dbh.insertNewUser("Ryan","H3W1N1","wolrd","railanderson@gmail.com"); //works
        //int a = (int) dbh.insertNewResto("test1","99.99","*****","chinese food",1,8500,8500,new byte[]{},"hello test"); //works
        int c = (int)dbh.insertNewAddress("Av. Saint-Kevin","4650","Montreal","H3W1N9",1); // works
        int d = (int)dbh.insertNewComment("Hello Word","testing","**",1,1);// works
        int e = (int)dbh.insertNewGenre("Chinese Food",1);// works
        Log.d(TAG,e+"");
    }

    private void testGetAllUsers(){
        List<User> users = dbh.getAllUsers();
        for(User u : users){
            Log.d(TAG,u.getName());
        }
    }

    private void testGetAllComments(){
        List<Comment> comments = dbh.getAllComments();
        for(Comment c : comments){
            Log.d(TAG, c.getTitle());
        }
    }

    private void testGetAllAddresses(){
        List<Address> addresses = dbh.getAllAddresses();
        for(Address a : addresses){
            Log.d(TAG, a.getCity());
        }
    }

    private void testGetCommentsById(){
        List<Comment> comments = dbh.getCommentsByUserId(1);
        for(Comment a : comments){
            Log.d(TAG, a.getContent());
        }
    }

    private void testGetCommentsByResto(){
        List<Comment> comments = dbh.getCommentsByResto(1);
        for(Comment a : comments){
            Log.d(TAG, a.getContent());
        }
    }
    private void testGetAddressesByResto(){
        List<Address> addresses = dbh.getAddressByRestoID(1);
        for(Address a: addresses){
            Log.d(TAG, a.getCity());
        }
    }

    private void testGetRestosByUId(){
        List<Restaurant> r = dbh.getRestaurantsByUserId(1);
        for(Restaurant a:r){
            //Log.d(TAG,a.getGenre());
        }
    }
}
