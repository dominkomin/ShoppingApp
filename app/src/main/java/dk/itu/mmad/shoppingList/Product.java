package dk.itu.mmad.shoppingList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by domi on 22-03-2015.
 */
public class Product implements Parcelable
{

	final int id;
	final String name;
	final int amount;

	public Product(String name, int amount, int id)
	{
		this.name = name;
		this.amount = amount;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public int getAmount()
	{
		return amount;
	}

	public int getId()
	{
		return id;
	}


	protected Product(Parcel in) {
		id = in.readInt();
		name = in.readString();
		amount = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(amount);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		@Override
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		@Override
		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}
