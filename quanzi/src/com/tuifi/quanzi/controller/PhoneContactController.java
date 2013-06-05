package com.tuifi.quanzi.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

import com.tuifi.quanzi.model.User;
import com.tuifi.quanzi.model.UserInfo;

public class PhoneContactController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "UserController";
	public static List<User> contactList = new ArrayList<User>();
	public static ContentResolver resolver;
	HashMap<Integer, User> allContact = new HashMap<Integer, User>();
	public static List<User> userList = new ArrayList<User>();
	/**
	 * 查询通讯录中的信息
	 * 
	 * @throws Throwable
	 */
	public void queryContacts() throws Throwable {
		// 获得contentresolver对象
		// 取得通讯录的Cursor
		/*
		 * 第一个参数是一个使用content:// scheme的URI 第二个参数是要返回的列名，为空则返回全部 第三个参数是where条件
		 * 第四个参数是条件的值 第五个参数是排序
		 */
		
		Uri uri = Uri.parse("content://com.android.contacts/contacts");// localhost="com.android.contacts"
		Cursor c = resolver.query(uri, null, null, null, null);
		Log.i(LOG, c.getCount() + "");
	}

	public void saveallcontact() throws Throwable {
		for (User user : userList) {
			SaveUser(user);
		}
	}

	/**
	 * 非事务的方式添加通讯录,插入数据的操作不在同一个事务中
	 * 
	 * @throws Throwable
	 */
	public void addContact(User user, UserInfo ui) throws Throwable {
		// 先向raw_contacts表中添加一条空记录,因为表中主要是存储的联系人的id
		// data表中主要是存储的联系人的信息,所以我们要先获得新增的联系人的id,id为自动增长		
		ContentValues values = new ContentValues();// 参数集合
		Uri url = resolver.insert(RawContacts.CONTENT_URI, values);// 插入空记录
		long id = ContentUris.parseId(url);

		// 往data表入姓名数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, id);// id
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);// 内容类型
		values.put(StructuredName.GIVEN_NAME, user.getuname());// 添加姓名
		resolver.insert(ContactsContract.Data.CONTENT_URI, values);

		// 往data表入电话数据
		values.clear();// 清空values中的数据
		values.put(Data.RAW_CONTACT_ID, id);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, user.getMobile());
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI,
				values);
		// 往data表入Email数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, id);
		values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		values.put(Email.DATA, user.getemail());
		values.put(Email.TYPE, Email.TYPE_WORK);
		resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI,
				values);
	}

	/**
	 * 向通讯录中添加数据,所有的操作在同一个事务中
	 * 
	 * @throws Throwable
	 */
	public void Save() throws Throwable {
		// 文档位置：reference\android\provider\ContactsContract.RawContacts.html
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = 1;
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
		// 文档位置：reference\android\provider\ContactsContract.Data.html
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.GIVEN_NAME, "赵薇").build());
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
				.withValue(Phone.NUMBER, "13671323809")
				.withValue(Phone.TYPE, Phone.TYPE_MOBILE)
				.withValue(Phone.LABEL, "手机号").build());
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
				.withValue(Email.DATA, "liming@itcast.cn")
				.withValue(Email.TYPE, Email.TYPE_WORK).build());
		resolver.applyBatch(ContactsContract.AUTHORITY, ops);

	}

	public void SaveUser(User user) throws Throwable {
		// 文档位置：reference\android\provider\ContactsContract.RawContacts.html
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = Integer.parseInt(user.contactId);
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
		// 文档位置：reference\android\provider\ContactsContract.Data.html
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.GIVEN_NAME, user.uname).build());
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
				.withValue(Phone.NUMBER, user.mobile)
				.withValue(Phone.TYPE, Phone.TYPE_MOBILE)
				.withValue(Phone.LABEL, "手机号").build());
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
				.withValue(Email.DATA, user.mobile)
				.withValue(Email.TYPE, Email.TYPE_WORK).build());
		resolver.applyBatch(ContactsContract.AUTHORITY, ops);

	}

	// 查询Content Provider时希望返回的列
	String[] columns = { ContactsContract.Contacts.DISPLAY_NAME,
			ContactsContract.Contacts._ID,
	// People._ID,
	// People.NAME
	};

	Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

	// 获取联系人列表的信息,返回 String对象
	public String getQueryData() {
		String result = "";
		// 获取ContentResolver对象		
		Cursor cursor = resolver.query(contactUri, columns, null, null, null);
		// 获得_ID字段的索引
		int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
		// 获得Name字段的索引
		int nameIndex = cursor
				.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		// 遍历Cursor提取数据
		for (cursor.moveToFirst(); (!cursor.isAfterLast()); cursor.moveToNext()) {
			result = result + cursor.getString(idIndex) + "\t";
			result = result + cursor.getString(nameIndex) + "\t\n";
		}
		cursor.close();
		return result;
	}

	public String GetAllContact() throws Throwable {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		StringBuilder sb = new StringBuilder();
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {

			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			sb.append("contactId=").append(contactId).append(",name=")
					.append(name);

			Cursor phones = resolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phones.moveToNext()) {
				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				sb.append(",phone=").append(phoneNumber);
			}
			phones.close();

			Cursor emails = resolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
							+ contactId, null, null);
			while (emails.moveToNext()) {
				String emailAddress = emails
						.getString(emails
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				sb.append(",emailAddress=").append(emailAddress);
			}
			emails.close();
			Log.i(LOG, sb.toString());
		}
		cursor.close();
		return sb.toString();
	}

	// 更新联系人

	public void update(String rawRawContactId, String NewNumber)

	{

		ContentValues values = new ContentValues();
		values.put(Phone.NUMBER, NewNumber);
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);

		String Where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND "
				+ ContactsContract.Data.MIMETYPE + " = ?";
		String[] WhereParams = new String[] { rawRawContactId,
				Phone.CONTENT_ITEM_TYPE, };
		resolver.update(ContactsContract.Data.CONTENT_URI, values,
				Where, WhereParams);

	}

	public HashMap<Integer, User> GetAllContactToMap() throws Throwable {
		HashMap<Integer, User> map = new HashMap<Integer, User>();
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		Cursor cursor = resolver.query(uri, null, null, null, null);
		User user = new User();
		while (cursor.moveToNext()) {
			StringBuilder sb = new StringBuilder();
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			sb.append("contactId=").append(contactId).append(",name=")
					.append(name);
			user.setcontactId(contactId);
			user.setuname(name);

			Cursor phones = resolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phones.moveToNext()) {
				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				sb.append(",phone=").append(phoneNumber);
				user.setMobile(phoneNumber);
			}

			phones.close();

			Cursor emails = resolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
							+ contactId, null, null);
			while (emails.moveToNext()) {
				String emailAddress = emails
						.getString(emails
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				sb.append(",emailAddress=").append(emailAddress);
				user.setemail(emailAddress);
			}
			emails.close();
			Log.i(LOG, sb.toString());
		}
		cursor.close();
		return null;
	}

	public void delete() {
		//ContentResolver cr = this.getBaseContext().getContentResolver();
		String where = ContactsContract.Data._ID + " = ? ";
		String[] params = new String[] { "" };

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(where, params).build());
		try {
			resolver.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 通过姓名(uName)来查找通讯录，返回一个list。 其中"display_name"保存姓名,"phone_number"保存电话
	 */
	public List<HashMap<String, String>> getContactsByName(String uName) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		boolean isQueryAll = false;

		// cu姓名游标，cn电话号码游标
		Cursor cu, cn = null;
		//ContentResolver contentResolver = this.getBaseContext().getContentResolver();
		// 查询条件，SQL是的Where语句的后部分
		String selection = null;
		Uri URI = ContactsContract.Data.CONTENT_URI;

		uName = uName.trim();
		// 是否查询全部通讯录,如果姓名为空则是
		isQueryAll = uName.equals("") ? true : false;

		if (isQueryAll) {
			// 查询全部时的，查询条件，主要用在cu游标上
			selection = ContactsContract.Data.MIMETYPE
					+ "='"
					+ ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
					+ "'";
			// System.out.println("Query For ALl--" + selection);
		} else {
			// 根据姓名查询时的，查询条件，主要用在cu游标上
			selection = ContactsContract.Data.MIMETYPE
					+ "='"
					+ ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
					+ "'"
					+ " AND "
					+ ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME
					+ " LIKE " + "'%" + uName + "%'";
			// System.out.println("Query For Some--" + selection);
		}

		try {
			// 根据姓名查询出完整姓名和通讯录ID
			cu = resolver
					.query(URI,
							new String[] {
									ContactsContract.Data.RAW_CONTACT_ID,
									ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME },
							selection, null, null);
			// 根据通讯录ID，查找对应的电话号码的查询条件，主要用于cn游标
			selection = ContactsContract.Data.MIMETYPE + "='"
					+ ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
					+ "'" + " AND " + ContactsContract.Data.RAW_CONTACT_ID
					+ "=?";
			// System.out.println("Number Query--" + selection);
			while (cu.moveToNext()) {
				String contactId = String.valueOf(cu.getInt(0));
				// 开始查找电话号码
				// System.out.println("  Start Query Num");
				cn = resolver
						.query(URI,
								new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
								selection, new String[] { contactId }, null);

				while (cn.moveToNext()) {
					// 将一组通讯录记录在HashMap中
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("display_name", cu.getString(1));
					map.put("phone_number", cn.getString(0));
					// 将查到通讯录添加到List中
					list.add(map);
				}
			}
			// 关闭游标
			cu.close();
			cn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
