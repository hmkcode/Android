package com.hmkcode.android;

import java.util.List;

import com.hmkcode.android.model.Book;
import com.hmkcode.android.sqlite.MySQLiteHelper;

import android.os.Bundle;
import android.app.Activity;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		MySQLiteHelper db = new MySQLiteHelper(this);
        
        /**
         * CRUD Operations
         * */
        // add Books
        db.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));   
        db.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));       
        db.addBook(new Book("Learn Android App Development", "Wallace Jackson"));
        
        // get all books
        List<Book> list = db.getAllBooks();
        
        // delete one book
        db.deleteBook(list.get(0));
        
        // get all books
        db.getAllBooks();

        
	}

}
