package dk.itu.mmad.shoppingList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by domi on 25-02-2015.
 */
public class ShoppingListSqlLiteHelper extends SQLiteOpenHelper
{
	public ShoppingListSqlLiteHelper(Context context)
	{
		super(context, "shoppingList2", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE products (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, amount INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE products");
		onCreate(db);
	}
}
