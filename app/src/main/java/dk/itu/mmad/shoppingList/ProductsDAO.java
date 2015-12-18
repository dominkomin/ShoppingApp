package dk.itu.mmad.shoppingList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by domi on 25-02-2015.
 */
public class ProductsDAO
{
	public final static String PRODUCTS_TABLE_NAME = "products";

	public final static String ID_PRODUCTS_COL = "_id";
	public final static String NAME_COL = "name";
	public final static String AMOUNT_COL = "amount";

	private ShoppingListSqlLiteHelper helper;
	private SQLiteDatabase db;

	private SharedPreferences preferences;

	public ProductsDAO(Context context)
	{
		helper = new ShoppingListSqlLiteHelper(context);
	}

	public void open()
	{
		db = helper.getWritableDatabase();
	}

	public void close()
	{
		helper.close();
	}

	public Cursor getProducts()
	{
		Cursor cursor = db.query(PRODUCTS_TABLE_NAME, new String[]{ID_PRODUCTS_COL, NAME_COL, AMOUNT_COL}, null, null, null, null, NAME_COL + " DESC");
		return cursor;
	}

	public void saveProduct(String name, int amount)
	{
		ContentValues values = new ContentValues();
		values.put(NAME_COL, name);
		values.put(AMOUNT_COL, amount);
		db.insert(PRODUCTS_TABLE_NAME, null, values);
	}

	public void updateProduct(Product product)
	{
		ContentValues values = new ContentValues();
		values.put(NAME_COL, product.getName());
		values.put(AMOUNT_COL, product.getAmount());

		int result = db.update(PRODUCTS_TABLE_NAME, values, ID_PRODUCTS_COL + " = ?",
				new String[] { String.valueOf(product.getId()) });
	}

	public void clearProducts()
	{
		db.delete(PRODUCTS_TABLE_NAME, null, null);
	}

	public void deleteProduct(int id)
	{
		db.delete(PRODUCTS_TABLE_NAME, ID_PRODUCTS_COL + " = ?",
				new String[] { String.valueOf(id) });
	}
}
