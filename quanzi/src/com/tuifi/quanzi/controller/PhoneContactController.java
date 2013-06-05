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
	 * ��ѯͨѶ¼�е���Ϣ
	 * 
	 * @throws Throwable
	 */
	public void queryContacts() throws Throwable {
		// ���contentresolver����
		// ȡ��ͨѶ¼��Cursor
		/*
		 * ��һ��������һ��ʹ��content:// scheme��URI �ڶ���������Ҫ���ص�������Ϊ���򷵻�ȫ�� ������������where����
		 * ���ĸ�������������ֵ ���������������
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
	 * ������ķ�ʽ���ͨѶ¼,�������ݵĲ�������ͬһ��������
	 * 
	 * @throws Throwable
	 */
	public void addContact(User user, UserInfo ui) throws Throwable {
		// ����raw_contacts�������һ���ռ�¼,��Ϊ������Ҫ�Ǵ洢����ϵ�˵�id
		// data������Ҫ�Ǵ洢����ϵ�˵���Ϣ,��������Ҫ�Ȼ����������ϵ�˵�id,idΪ�Զ�����		
		ContentValues values = new ContentValues();// ��������
		Uri url = resolver.insert(RawContacts.CONTENT_URI, values);// ����ռ�¼
		long id = ContentUris.parseId(url);

		// ��data������������
		values.clear();
		values.put(Data.RAW_CONTACT_ID, id);// id
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);// ��������
		values.put(StructuredName.GIVEN_NAME, user.getuname());// �������
		resolver.insert(ContactsContract.Data.CONTENT_URI, values);

		// ��data����绰����
		values.clear();// ���values�е�����
		values.put(Data.RAW_CONTACT_ID, id);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, user.getMobile());
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI,
				values);
		// ��data����Email����
		values.clear();
		values.put(Data.RAW_CONTACT_ID, id);
		values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		values.put(Email.DATA, user.getemail());
		values.put(Email.TYPE, Email.TYPE_WORK);
		resolver.insert(android.provider.ContactsContract.Data.CONTENT_URI,
				values);
	}

	/**
	 * ��ͨѶ¼���������,���еĲ�����ͬһ��������
	 * 
	 * @throws Throwable
	 */
	public void Save() throws Throwable {
		// �ĵ�λ�ã�reference\android\provider\ContactsContract.RawContacts.html
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = 1;
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
		// �ĵ�λ�ã�reference\android\provider\ContactsContract.Data.html
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.GIVEN_NAME, "��ޱ").build());
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
				.withValue(Phone.NUMBER, "13671323809")
				.withValue(Phone.TYPE, Phone.TYPE_MOBILE)
				.withValue(Phone.LABEL, "�ֻ���").build());
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
		// �ĵ�λ�ã�reference\android\provider\ContactsContract.RawContacts.html
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = Integer.parseInt(user.contactId);
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
		// �ĵ�λ�ã�reference\android\provider\ContactsContract.Data.html
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
				.withValue(Phone.LABEL, "�ֻ���").build());
		ops.add(ContentProviderOperation
				.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID,
						rawContactInsertIndex)
				.withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
				.withValue(Email.DATA, user.mobile)
				.withValue(Email.TYPE, Email.TYPE_WORK).build());
		resolver.applyBatch(ContactsContract.AUTHORITY, ops);

	}

	// ��ѯContent Providerʱϣ�����ص���
	String[] columns = { ContactsContract.Contacts.DISPLAY_NAME,
			ContactsContract.Contacts._ID,
	// People._ID,
	// People.NAME
	};

	Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

	// ��ȡ��ϵ���б����Ϣ,���� String����
	public String getQueryData() {
		String result = "";
		// ��ȡContentResolver����		
		Cursor cursor = resolver.query(contactUri, columns, null, null, null);
		// ���_ID�ֶε�����
		int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
		// ���Name�ֶε�����
		int nameIndex = cursor
				.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		// ����Cursor��ȡ����
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

	// ������ϵ��

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
	 * ͨ������(uName)������ͨѶ¼������һ��list�� ����"display_name"��������,"phone_number"����绰
	 */
	public List<HashMap<String, String>> getContactsByName(String uName) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		boolean isQueryAll = false;

		// cu�����α꣬cn�绰�����α�
		Cursor cu, cn = null;
		//ContentResolver contentResolver = this.getBaseContext().getContentResolver();
		// ��ѯ������SQL�ǵ�Where���ĺ󲿷�
		String selection = null;
		Uri URI = ContactsContract.Data.CONTENT_URI;

		uName = uName.trim();
		// �Ƿ��ѯȫ��ͨѶ¼,�������Ϊ������
		isQueryAll = uName.equals("") ? true : false;

		if (isQueryAll) {
			// ��ѯȫ��ʱ�ģ���ѯ��������Ҫ����cu�α���
			selection = ContactsContract.Data.MIMETYPE
					+ "='"
					+ ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
					+ "'";
			// System.out.println("Query For ALl--" + selection);
		} else {
			// ����������ѯʱ�ģ���ѯ��������Ҫ����cu�α���
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
			// ����������ѯ������������ͨѶ¼ID
			cu = resolver
					.query(URI,
							new String[] {
									ContactsContract.Data.RAW_CONTACT_ID,
									ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME },
							selection, null, null);
			// ����ͨѶ¼ID�����Ҷ�Ӧ�ĵ绰����Ĳ�ѯ��������Ҫ����cn�α�
			selection = ContactsContract.Data.MIMETYPE + "='"
					+ ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
					+ "'" + " AND " + ContactsContract.Data.RAW_CONTACT_ID
					+ "=?";
			// System.out.println("Number Query--" + selection);
			while (cu.moveToNext()) {
				String contactId = String.valueOf(cu.getInt(0));
				// ��ʼ���ҵ绰����
				// System.out.println("  Start Query Num");
				cn = resolver
						.query(URI,
								new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
								selection, new String[] { contactId }, null);

				while (cn.moveToNext()) {
					// ��һ��ͨѶ¼��¼��HashMap��
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("display_name", cu.getString(1));
					map.put("phone_number", cn.getString(0));
					// ���鵽ͨѶ¼��ӵ�List��
					list.add(map);
				}
			}
			// �ر��α�
			cu.close();
			cn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
