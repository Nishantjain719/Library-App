package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want-to-read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVORITE_BOOKS = "favorite_books";

    private static Utils instance;
    private SharedPreferences sharedPreferences;

    //private static ArrayList<Book> allBooks;
    //private static ArrayList<Book> alreadyReadBooks;
    //private static ArrayList<Book> wantToReadBooks;
    //private static ArrayList<Book> currentlyReadingBooks;
    //private static ArrayList<Book> favoriteBooks;

    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);
        if (null == getAllBooks()) {

            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()) {
            /**
             * initializing Already Read Books as an Empty Arraylist inside our sharedpreferences
             */
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getWantToReadBooks()) {
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getCurrentlyReadingBooks()) {
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getFavoriteBooks()) {
            editor.putString(FAVORITE_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

    }

    private void initData() {
        //TODO: add initial data

        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1, "Glimpses of World History", "Jawaharlal Nehru", 500, "https://upload.wikimedia.org/wikipedia/en/3/39/Glimpses_of_World_History_book_cover.jpg", "short", "long"));
        books.add(new Book(2, "Hind Swaraj", "M K Gandhi", 400, "https://images-na.ssl-images-amazon.com/images/I/41yj6jdiIAL._SX318_BO1,204,203,200_.jpg", "hello", "dear"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();

    }

    public static Utils getInstance(Context context) {
        if (null != instance) {
            return instance;
        }else {
            instance = new Utils(context);
            return instance;
        }

    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY,null), type);

        return books;
    }

    public  ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS,null), type);
        return books;

    }

    public  ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS,null), type);
        return books;

    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS,null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS,null), type);
        return books;
    }

    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (null != books) {
            for (Book b: books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }


        return null;
    }

    public boolean addToAlreadyRead(Book book) {
        ArrayList<Book> books =getAlreadyReadBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                /**
                 * remove current already read book and after that put this new books arraylist
                 */
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;

            }
        }
        return false;

    }

    public boolean addToWantToRead(Book book) {
        ArrayList<Book> books =getWantToReadBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                /**
                 * remove current already read book and after that put this new books arraylist
                 */
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;

            }
        }
        return false;
    }

    public boolean addToFavorite(Book book) {
        ArrayList<Book> books =getFavoriteBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                /**
                 * remove current already read book and after that put this new books arraylist
                 */
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                editor.commit();
                return true;

            }
        }
        return false;
    }

    public boolean addToCurrentlyRead(Book book) {
        ArrayList<Book> books =getCurrentlyReadingBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                /**
                 * remove current already read book and after that put this new books arraylist
                 */
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                editor.commit();
                return true;

            }
        }
        return false;
    }

    public boolean removeFromAlreadyRead(Book book) {
        ArrayList<Book> books =getAlreadyReadBooks();
        if (null != books) {
            for (Book b: books) {
               if (b.getId() == book.getId()) {
                   if (books.remove(b)) {
                       Gson gson = new Gson();
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       /**
                        * remove current already read book and after that put this new books arraylist
                        */
                       editor.remove(ALREADY_READ_BOOKS);
                       editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                       editor.commit();
                       return true;
                   }
               }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book) {
        ArrayList<Book> books =getWantToReadBooks();
        if (null != books) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        /**
                         * remove current already read book and after that put this new books arraylist
                         */
                        editor.remove(WANT_TO_READ_BOOKS);
                        editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean removeFromCurrentlyRead(Book book) {
        ArrayList<Book> books =getCurrentlyReadingBooks();
        if (null != books) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        /**
                         * remove current already read book and after that put this new books arraylist
                         */
                        editor.remove(CURRENTLY_READING_BOOKS);
                        editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean removeFromFavorites(Book book) {
        ArrayList<Book> books =getFavoriteBooks();
        if (null != books) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        /**
                         * remove current already read book and after that put this new books arraylist
                         */
                        editor.remove(FAVORITE_BOOKS);
                        editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    }




