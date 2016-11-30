package com.nough.gouda.goudanough.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nough.gouda.goudanough.beans.Address;
import com.nough.gouda.goudanough.beans.Comment;
import com.nough.gouda.goudanough.beans.Restaurant;
import com.nough.gouda.goudanough.beans.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has the purpose of dealing
 * with all the CRUD operations involving our database.
 * Created by Ryan Sena on 11/23/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "DB";// for logging

    //table names.
    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String TABLE_USERS = "user";
    public static final String TABLE_COMMENTS = "comment";
    public static final String TABLE_GENRE = "genre";
    public static final String TABLE_ADDRESS = "address";

    //database field names

    //RESTO TABLE
    public static final String COLUMN_RESTOID = "_id";
    public static final String COLUMN_RESTO_NAME = "name";
    public static final String COLUMN_PRICERANGE = "priceRange";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_PHONENUMBER = "phoneNumber";
    public static final String COLUMN_CUISINE = "cuisine";
    public static final String COLUMN_RESTO_USERID = "userID";
    public static final String COLUMN_LONG = "longitude";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_URL = "url";
    //COMMENT TABLE
    public static final String COLUMN_COMMENTID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COMM_RATING = "rating";
    public static final String COLUMN_CONTENT = "comment";
    public static final String COLUMN_COMM_RESTOID = "restoID";
    public static final String COLUMN_COMM_USERID = "userID";
    //USER TABLE
    public static final String COLUMN_USERID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USER_POSTALCODE = "postalCode";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_EMAIL = "email";
    //ADDRESS TABLE
    public static final String COLUMN_ADDRESSID = "_id";
    public static final String COLUMN_STREETNAME = "streetName";
    public static final String COLUMN_STREETNUMBER = "streetNumber";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_POSTALCODE = "postalCode";
    public static final String COLUMN_ADDR_RESTOID = "restoID";

    //GENRE TABLE
    public static final String COLUMN_GENREID = "_id";
    public static final String COLUMN_GENRENME = "genreName";
    public static final String COLUMN_GEN_RESTOID = "restoID";


    //Foreign keys
    public static final String FK_RESTO_USER = "FOREIGN KEY (" +COLUMN_RESTO_USERID +" ) REFERENCES " + TABLE_USERS +"("+ COLUMN_USERID + ") ";
    public static final String FK_COMM_RESTO = "FOREIGN KEY (" +COLUMN_COMM_RESTOID +" ) REFERENCES " + TABLE_RESTAURANT +"("+ COLUMN_RESTOID + ") ";
    public static final String FK_COMM_USER = "FOREIGN KEY (" +COLUMN_COMM_USERID +" ) REFERENCES " + TABLE_USERS +"( "+ COLUMN_USERID + ") ";
    public static final String FK_GEN_RESTO = "FOREIGN KEY (" +COLUMN_GEN_RESTOID +" ) REFERENCES " + TABLE_RESTAURANT +"( "+ COLUMN_RESTOID + ") ";
    public static final String FK_ADDR_RESTO = "FOREIGN KEY (" +COLUMN_ADDR_RESTOID +" ) REFERENCES " + TABLE_RESTAURANT +"( "+ COLUMN_RESTOID + ") ";

    //DB NAME
    public static final String DATABASE_NAME = "goudanough.db";
    //DB Version, for when onUpdate is called.
    public static final int DATABASE_VERSION = 1;

    private static DBHelper dbh = null;
    //Database creation raw SQL statement
    private static final String DATABASE_CREATE_RESTAURANT = "create table " + TABLE_RESTAURANT
            + "( "
            + COLUMN_RESTOID + " integer primary key autoincrement, "
            + COLUMN_RESTO_NAME + " text, "
            + COLUMN_PRICERANGE + " int, "
            + COLUMN_PHONENUMBER + " text, "
            + COLUMN_RATING + " text, "
            + COLUMN_CUISINE + " text, "
            + COLUMN_RESTO_USERID + " integer, "
            + COLUMN_LONG + " real, "
            + COLUMN_LAT + " real, "
            + COLUMN_IMG + " blob, "
            + COLUMN_URL + " text, "
            + FK_RESTO_USER
            +");";

    private static final String DATABASE_CREATE_USER = "create table " + TABLE_USERS
            + "( "
            + COLUMN_USERID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text unique, "
            + COLUMN_USER_POSTALCODE + " text, "
            + COLUMN_PASS + " text, "
            + COLUMN_EMAIL + " text"
            + ");";

    private static final String DATABASE_CREATE_COMMENTS = "create table " + TABLE_COMMENTS
            + "( "
            + COLUMN_COMMENTID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text, "
            + COLUMN_COMM_RATING + " text, "
            + COLUMN_CONTENT + " text, "
            + COLUMN_COMM_RESTOID + " integer, "
            + COLUMN_COMM_USERID + " integer, "
            + FK_COMM_RESTO + ", "
            + FK_COMM_USER
            + ");";

    private static final String DATABASE_CREATE_GENRE = "create table " + TABLE_GENRE
            + "( "
            + COLUMN_GENREID + " integer primary key autoincrement,"
            + COLUMN_GENRENME + " text, "
            + COLUMN_GEN_RESTOID + " integer, "
            + FK_GEN_RESTO
            + ");";

    private static final String DATABASE_CREATE_ADDRESS = "create table " + TABLE_ADDRESS
            + "( "
            + COLUMN_ADDRESSID + " integer primary key autoincrement, "
            + COLUMN_STREETNAME + " text, "
            + COLUMN_STREETNUMBER + " text, "
            + COLUMN_CITY + " text, "
            + COLUMN_POSTALCODE + " text, "
            + COLUMN_ADDR_RESTOID + " integer, "
            + FK_ADDR_RESTO
            +");";


    /**
     * Default Constructor.
     *
     * @param context
     */
    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method takes care of creating all my tables.
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) throws SQLException{

        database.execSQL(DATABASE_CREATE_RESTAURANT);
        Log.i(TAG, "CREATE RESTO TBL");
        database.execSQL(DATABASE_CREATE_USER);
        Log.i(TAG, "CREATE USER TBL");
        database.execSQL(DATABASE_CREATE_COMMENTS);
        Log.i(TAG, "CREATE COMMENTS TBL");
        database.execSQL(DATABASE_CREATE_GENRE);
        Log.i(TAG, "CREATE GENRE TBL");
        database.execSQL(DATABASE_CREATE_ADDRESS);
        Log.i(TAG, "CREATE ADDRESS TBL");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)throws SQLException{
        Log.w(TAG, DBHelper.class.getName() + "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        // Im dropping all the tables in order to create new ones.
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRE);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(database);
        Log.i(TAG, "UPGRADED");
    }
    @Override
    public void onOpen(SQLiteDatabase database) {
        Log.i(TAG, "OPENED");
    }

    /**
     * This method will insert a new Restaurant record to the database.
     * @param resto
     * @param userId
     * @return
     */
    public long insertNewResto(Restaurant resto, int userId)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESTO_NAME,resto.getName());
        cv.put(COLUMN_PRICERANGE,resto.getPrice_range());
        cv.put(COLUMN_RATING,resto.getRating());
        cv.put(COLUMN_CUISINE,resto.getCuisine());
        cv.put(COLUMN_RESTO_USERID,userId);
        cv.put(COLUMN_LONG,resto.getLongitude());
        cv.put(COLUMN_LAT,resto.getLatitude());
        cv.put(COLUMN_IMG,resto.getFeatured_image());
        cv.put(COLUMN_URL,resto.getUrl());

        return getWritableDatabase().insert(TABLE_RESTAURANT, null, cv);


    }

    /**
     * This method will take care of inserting a new user record to the database.
     *
     * @param name name of the person.
     * @param postalCode person's postal code.
     * @param pass user's password.
     * @param email user's email.
     * @return the number of rows affected.
     */
    public long insertNewUser(String name, String postalCode, String pass, String email){

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_USER_POSTALCODE,postalCode);
        cv.put(COLUMN_PASS,pass);
        cv.put(COLUMN_EMAIL,email);

        return getWritableDatabase().insert(TABLE_USERS, null, cv);
    }

    /**
     * This method will take care of inserting a new comment to the database.
     *
     * @param title the title of the comment.
     * @param content the content of the comment.
     * @param rating the rating of the comment.
     * @param userID who made the comment.
     * @param restoID to which resto this comment belongs to.
     * @return the number of rows affected.
     */
    public long insertNewComment(String title, String content, String rating, int userID, int restoID){

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_COMM_RATING,rating);
        cv.put(COLUMN_CONTENT,content);
        cv.put(COLUMN_COMM_RESTOID,restoID);
        cv.put(COLUMN_COMM_USERID,userID);

        return getWritableDatabase().insert(TABLE_COMMENTS, null, cv);
    }

    /**
     * This method will take care of inserting a new Genre to the database.
     * @param genreName the name of the resto genre for example "traditioanl chinese food"
     * @param restoID which resto this genre belongs to.
     * @return number of rows affected.
     */
    public long insertNewGenre(String genreName, int restoID){

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GENRENME,genreName);
        cv.put(COLUMN_GEN_RESTOID,restoID);

        return getWritableDatabase().insert(TABLE_GENRE, null, cv);
    }

    /**
     * This method will take care of inserting new addresses to the database.
     *
     * @param streetName
     * @param streetNumber
     * @param city
     * @param postalCode
     * @return the number of rows affected.
     */
    public long insertNewAddress(String streetName, String streetNumber, String city, String postalCode, int restoID){

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STREETNAME,streetName);
        cv.put(COLUMN_STREETNUMBER,streetNumber);
        cv.put(COLUMN_CITY,city);
        cv.put(COLUMN_POSTALCODE,postalCode);
        cv.put(COLUMN_ADDR_RESTOID,restoID);


        return getWritableDatabase().insert(TABLE_ADDRESS, null, cv);
    }

    /**
     * This method will return all the restaurants in the database.
     * P.S. This list doesnt have the addresses and other stuff.. must complete it with other methdos
     * @return
     */
    public List<Restaurant> getAllRestaurants(){

        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant resto = new Restaurant();

        Cursor c = getReadableDatabase().query(TABLE_RESTAURANT,null,null, null, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    resto.setId(c.getInt(c.getColumnIndex(COLUMN_RESTOID)));
                    resto.setName(c.getString(c.getColumnIndex(COLUMN_RESTO_NAME)));
                    resto.setLatitude(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                    resto.setLongitude(c.getDouble(c.getColumnIndex(COLUMN_LONG)));
                    resto.setCuisine(c.getString(c.getColumnIndex(COLUMN_CUISINE)));
                    resto.setPrice_range(c.getInt(c.getColumnIndex(COLUMN_PRICERANGE)));
                    resto.setUrl(c.getString(c.getColumnIndex(COLUMN_URL)));

                    restaurants.add(resto);
                    resto = new Restaurant();
                }while(c.moveToNext());
            }
        }
        return restaurants;

    }

    /**
     * This method will return all the users in the database.
     * @return
     */
    public List<User> getAllUsers(){
        List<User> listOfUsers = new ArrayList<>();
        User user = new User();

        Cursor c = getReadableDatabase().query(TABLE_USERS,null,null, null, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    user.setId(c.getInt(c.getColumnIndex(COLUMN_USERID)));
                    user.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
                    user.setEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
                    user.setPass(c.getString(c.getColumnIndex(COLUMN_PASS)));
                    user.setPostalCode(c.getString(c.getColumnIndex(COLUMN_USER_POSTALCODE)));

                    listOfUsers.add(user);
                    user = new User();
                }while(c.moveToNext());
            }
        }
        return listOfUsers;
    }

    /**
     * This method will return all comments in the database.
     * @return
     */
    public List<Comment> getAllComments(){
        List<Comment> listOfCommments = new ArrayList<>();
        Comment comment = new Comment();

        Cursor c = getReadableDatabase().query(TABLE_COMMENTS,null,null, null, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    comment.setId(c.getInt(c.getColumnIndex(COLUMN_COMMENTID)));
                    comment.setContent(c.getString(c.getColumnIndex(COLUMN_CONTENT)));
                    comment.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                    comment.setRating(c.getString(c.getColumnIndex(COLUMN_RATING)));

                    listOfCommments.add(comment);
                    comment = new Comment();
                }while(c.moveToNext());
            }
        }
        return listOfCommments;
    }

    /**
     * This method will return all the genres in the database.
     * @return
     */
    public Cursor getAllGenres(){
        return getReadableDatabase().query(TABLE_GENRE, null, null, null,
                null,null,null);
    }

    /**
     * This method will return all the addresses in the database.
     * @return
     */
    public List<Address> getAllAddresses(){
        List<Address> listOfAddresses = new ArrayList<>();
        Address address = new Address();

        Cursor c = getReadableDatabase().query(TABLE_ADDRESS,null,null, null, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    address.setId(c.getInt(c.getColumnIndex(COLUMN_ADDRESSID)));
                    address.setPostalCode(c.getString(c.getColumnIndex(COLUMN_POSTALCODE)));
                    address.setCity(c.getString(c.getColumnIndex(COLUMN_CITY)));
                    address.setStreetName(c.getString(c.getColumnIndex(COLUMN_STREETNAME)));
                    address.setStreetNumber(c.getString(c.getColumnIndex(COLUMN_STREETNUMBER)));

                    listOfAddresses.add(address);
                    address = new Address();
                }while(c.moveToNext());
            }
        }
        return listOfAddresses;
    }

    /**
     * This method will get all the comments based on the user ID
     * @return
     */
    public List<Comment> getCommentsByUserId(int userID){

        List<Comment> listOfComments = new ArrayList<>();
        Comment comment = new Comment();
        String[] tableColumns = new String[]{COLUMN_TITLE,COLUMN_COMM_RATING,COLUMN_CONTENT};
        String whereClause = COLUMN_USERID + " = ?";
        String[] whereArgs = new String[]{userID+""};

        Cursor c = getReadableDatabase().query(TABLE_COMMENTS,tableColumns,whereClause, whereArgs, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    comment.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                    comment.setContent(c.getString(c.getColumnIndex(COLUMN_CONTENT)));
                    comment.setRating(c.getString(c.getColumnIndex(COLUMN_COMM_RATING)));

                    listOfComments.add(comment);
                    comment = new Comment();
                }while(c.moveToNext());
            }
        }
        return listOfComments;// return the list.
    }

    /**
     * This method will get all the comments from a particular restaurant.
     * @param restoID
     * @return
     */
    public List<Comment> getCommentsByResto(int restoID){

        List<Comment> listOfComments = new ArrayList<>();
        Comment comment = new Comment();
        String[] tableColumns = new String[]{COLUMN_TITLE,COLUMN_COMM_RATING,COLUMN_CONTENT};
        String whereClause = COLUMN_COMM_RESTOID + " = ?";
        String[] whereArgs = new String[]{restoID+""};

        Cursor c = getReadableDatabase().query(TABLE_COMMENTS,tableColumns,whereClause, whereArgs, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    comment.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                    comment.setContent(c.getString(c.getColumnIndex(COLUMN_CONTENT)));
                    comment.setRating(c.getString(c.getColumnIndex(COLUMN_COMM_RATING)));

                    listOfComments.add(comment);
                    comment = new Comment();
                }while(c.moveToNext());
            }
        }
        return listOfComments;// return the list.
    }

    public static DBHelper getDBHelper(Context context){
            dbh = new DBHelper(context.getApplicationContext());
        return dbh;
    }

    /**
     * This method will get an adrress from a particular resto.
     * @param restoID
     * @return
     */
    public List<Address> getAddressByRestoID(int restoID){
        List<Address> listOfAddresses = new ArrayList<>();
        Address address = new Address();
        String whereClause = COLUMN_ADDR_RESTOID+ " = ?";
        String[] whereArgs = new String[]{restoID+""};
        Cursor c = getReadableDatabase().query(TABLE_ADDRESS,null,whereClause, whereArgs, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    address.setCity(c.getString(c.getColumnIndex(COLUMN_CITY)));
                    address.setId(c.getInt(c.getColumnIndex(COLUMN_ADDRESSID)));
                    address.setPostalCode(c.getString(c.getColumnIndex(COLUMN_POSTALCODE)));
                    address.setStreetName(c.getString(c.getColumnIndex(COLUMN_STREETNAME)));
                    address.setStreetNumber(c.getString(c.getColumnIndex(COLUMN_STREETNUMBER)));


                    listOfAddresses.add(address);
                    address = new Address();
                }while(c.moveToNext());
            }
        }
        return listOfAddresses;// return the list.
    }

    /**
     * This method will get all the restaurants by user ID
     * basically, when the user clicks on Favourite restos,
     * it will
     * @param userID
     * @return
     */
    public List<Restaurant> getRestaurantsByUserId(int userID){
        List<Restaurant> listOfRestos = new ArrayList<>();
        Restaurant resto = new Restaurant();
        String whereClause = COLUMN_RESTO_USERID + " = ?";
        String[] whereArgs = new String[]{userID+""};

        Cursor c = getReadableDatabase().query(TABLE_RESTAURANT,null,whereClause, whereArgs, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    resto.setId(c.getInt(c.getColumnIndex(COLUMN_RESTOID)));
                    resto.setName(c.getString(c.getColumnIndex(COLUMN_RESTO_NAME)));
                    resto.setLatitude(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                    resto.setLongitude(c.getDouble(c.getColumnIndex(COLUMN_LONG)));
                    resto.setCuisine(c.getString(c.getColumnIndex(COLUMN_CUISINE)));
                    resto.setPrice_range(c.getInt(c.getColumnIndex(COLUMN_PRICERANGE)));
                    resto.setUrl(c.getString(c.getColumnIndex(COLUMN_URL)));

                    listOfRestos.add(resto);
                    resto = new Restaurant();
                }while(c.moveToNext());
            }
        }
        return listOfRestos;// return the list.
    }

    /**
     * This method will get a list of user ids based on their username.
     *
     * @param userName the unique userName
     * @return the user ID for that.
     */
    public int getUserIdByUserName(String userName){
        int userID = 0;
        String whereClause = COLUMN_NAME + " = ?";
        String[] whereArgs = new String[]{userName};
        String[] tableColumns = new String[]{COLUMN_USERID};
        Cursor c = getReadableDatabase().query(TABLE_USERS,tableColumns,whereClause, whereArgs, null,
                null,null);// query and get the results as a cursor.
        if(c != null){
            if(c.moveToFirst()){// move the cursor to the first one
                do{// loop trough each record, getting the values column by column and adding to a
                    // list of comments
                    userID = c.getInt(c.getColumnIndex(COLUMN_USERID));
                }while(c.moveToNext());// should iterate only once because usernames are uniques.
            }
        }
        return userID;
    }
}
