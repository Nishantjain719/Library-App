package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthor, txtPages, txtDescription, txtLongerDesc;
    private Button btnCurrentlyReading, btnWantToRead, btnAlreadyRead, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        //TODO: Get the data from recycler view in here
        // Book book = new Book(1, "Glimpses of World History", "Jawaharlal Nehru", 500, "https://upload.wikimedia.org/wikipedia/en/3/39/Glimpses_of_World_History_book_cover.jpg", "short", "Image result for long description" + "\n" + "A long description is a way to provide long alternative text for non-text elements, such as images. ... Examples of suitable use of long description are charts, graphs, maps, infographics");

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }

            }
        }


    }

    private void handleFavoriteBooks(Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;
        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInFavoriteBooks = true;
            }
        }


        if (existInFavoriteBooks) {
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToFavorite(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        /**
                         * TODO has done
                         */
                        Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void handleCurrentlyReadingBooks(Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadingBooks = false;
        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInCurrentlyReadingBooks = true;
            }
        }


        if (existInCurrentlyReadingBooks) {
            btnCurrentlyReading.setEnabled(false);
        } else {
            btnCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        /**
                         * TODO has done
                         */
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;
        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInWantToReadBooks = true;
            }
        }


        if (existInWantToReadBooks) {
           btnWantToRead.setEnabled(false);
        } else {
            btnWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        /**
                         * TODO has done
                         */
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    /**
     * Enable and Disable button,
     * ADD the book to alreadyReadBook Arraylist
     *
     * @param book
     */
    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;
        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existInAlreadyReadBooks = true;
            }
        }


        if (existInAlreadyReadBooks) {
            btnAlreadyRead.setEnabled(false);
        } else {
            btnAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        //TODO: navigate the user
                        /**
                         * TODO has done
                         */
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getShortDesc());

        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }

    private void initViews() {
        txtAuthor = findViewById(R.id.txtAuthorName);
        txtBookName = findViewById(R.id.txtBookNam);
        txtPages = findViewById(R.id.txtPages);
        txtLongerDesc = findViewById(R.id.txtDescription);
        txtDescription = findViewById(R.id.descriptionText);

        btnWantToRead = findViewById(R.id.btnWanttoread);
        btnAlreadyRead = findViewById(R.id.btnAlreadyread);
        btnCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorite = findViewById(R.id.btnAddtofavorites);

        bookImage = findViewById(R.id.imageView);

    }


}