package com.example.android.inventory;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.BookContract;
import com.example.android.inventory.data.BookContract.BookEntry;
/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }
    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context,  final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        final TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        final Button saleButton = (Button) view.findViewById(R.id.sale_button);
        saleButton.setText(R.string.sale_button);

        final Button buyButton = (Button) view.findViewById(R.id.buy_button);
        buyButton.setText(R.string.buy_button);

        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        final String bookQuantity = cursor.getString(quantityColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);

        /* Update the textViews with the attibutes for the current product */
        nameTextView.setText(bookName);
        priceTextView.setText("$ " + bookPrice);
        quantityTextView.setText(bookQuantity);

        /* OnClickListener for saleButton */
        saleButton.setOnClickListener(new View.OnClickListener() {

            int rowId = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));
            Uri content = Uri.withAppendedPath(BookEntry.CONTENT_URI, Integer.toString(rowId));

            @Override
            public void onClick(View view) {

                saleButton.setEnabled(true);
                String previousQuantity = bookQuantity;
                int updatedQuantity = Integer.parseInt(previousQuantity);

                updatedQuantity = updatedQuantity - 1;
                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_BOOK_QUANTITY, updatedQuantity);
                int rowsUpdate = context.getContentResolver().update(content, values, null, null);

                Log.v("BookAdapter", "Values of _ID (2)" + rowId);

                //int rowsUpdate = context.getContentResolver().update(uri, values, null,null);
                if (rowsUpdate != 0) {
                    Toast.makeText(context, "Update was successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update is Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

        });

        /* OnClickListener for buyButton */
        buyButton.setOnClickListener(new View.OnClickListener() {

            int rowId = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));
            Uri content = Uri.withAppendedPath(BookEntry.CONTENT_URI, Integer.toString(rowId));

            @Override
            public void onClick(View view) {

                saleButton.setEnabled(true);
                String previousQuantity = bookQuantity;
                int updatedQuantity = Integer.parseInt(previousQuantity);
                if(updatedQuantity < 1){
                    Toast.makeText(context,"Product Sold Out",Toast.LENGTH_SHORT).show();
                }
                else {
                    updatedQuantity = updatedQuantity + 10;
                    ContentValues values =new ContentValues();
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, updatedQuantity);
                    int rowsUpdate = context.getContentResolver().update(content, values, null, null);

                    Log.v("BookAdapter","Values of _ID (2)" + rowId);

                    //int rowsUpdate = context.getContentResolver().update(uri, values, null,null);
                    if (rowsUpdate!=0){
                        Toast.makeText(context,"Update was successful",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context,"Update is Unsuccessful",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }
}
