package com.example.android.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {

    private BookContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_BOOKS = "books";

    /* Inner class that defines the table contents of the location table */
    public static final class BookEntry implements BaseColumns {


        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of books.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        /**
         * The MIME type of the {@link #CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        // Table name
        public static final String TABLE_NAME = "books";

        // Column with the foreign key into the pet table.
        public static final String _ID = BaseColumns._ID;


        // Name,
        public static final String COLUMN_BOOK_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_SUPPLIER_NAME = "supplier";
        public static final String COLUMN_BOOK_SUPPLIER_PHONE_NUMBER = "phone";
        public static final String COLUMN_BOOK_GENDER = "gender";

        /**
         * posible values for the gender
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_NONFICTION = 1;
        public static final int GENDER_FICTION = 2;

        /**
         * Returns whether or not the given gender is {@link #GENDER_NONFICTION}, {@link #GENDER_FICTION, , {@link #GENDER_UNKNOWN}
         */
        public static boolean isValidGender(int gender) {
            if (gender == GENDER_NONFICTION || gender == GENDER_FICTION || gender == GENDER_UNKNOWN) {
                return true;
            }
            return false;
        }
    }


}

