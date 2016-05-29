package com.newppt.android.logical;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;

import com.newppt.android.entity.MyPath;
import com.newppt.android.entity.PPTFileInfo;

public class FileInfo {

	/**
	 * �����ļ���
	 * 
	 * @param savePath
	 */
	public static void CreateFile(String savePath) {
		File file = new File(savePath);

		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}

	public static boolean fileExist(String savePath) {
		File file = new File(savePath);
		return file.exists();
	}

	/**
	 * ��ȡ·��������JPG����Ϣ
	 * 
	 * @param path
	 * @return
	 */
	public static List<JpgPathInfo> getJpgPathInfos(String path) {
		File pptFile = new File(path + MyPath.pptJpg);
		List<JpgPathInfo> infos = new ArrayList<JpgPathInfo>();
		JpgPathInfo info;

		String[] fileNames = pptFile.list();

		for (String name : fileNames) {
			info = new JpgPathInfo();
			info.setPptJpg(path + MyPath.pptJpg + "/" + name);
			File noteFile = new File(path + MyPath.noteJpg + "/" + name);
			if (noteFile.exists()) {
				info.setNoteJpg(path + MyPath.noteJpg + "/" + name);
			}

			infos.add(info);

		}

		return infos;
	}
	
	public static List<JpgPathInfo> getPPTAndNote(Cursor cursor) {
		List<JpgPathInfo> infos = new ArrayList<JpgPathInfo>();
		while(cursor.moveToNext()) {
			JpgPathInfo info = new JpgPathInfo();
			info.setPptJpg(cursor.getString(0));
			info.setNoteJpg(cursor.getString(1));
			infos.add(info);
		}
		return infos;
	}

	/**
	 * ����·��ɾ��ָ����Ŀ¼���ļ������۴������
	 *
	 * @param sPath
	 *            Ҫɾ����Ŀ¼���ļ�
	 * @return ɾ���ɹ����� true�����򷵻� false��
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// �ж�Ŀ¼���ļ��Ƿ����
		if (!file.exists()) { // �����ڷ��� false
			return flag;
		} else {
			// �ж��Ƿ�Ϊ�ļ�
			if (file.isFile()) { // Ϊ�ļ�ʱ����ɾ���ļ�����
				return deleteFile(sPath);
			} else { // ΪĿ¼ʱ����ɾ��Ŀ¼����
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * ɾ�������ļ�
	 * 
	 * @param sPath
	 *            ��ɾ���ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�
	 * 
	 * @param sPath
	 *            ��ɾ��Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteDirectory(String sPath) {
		// ���sPath�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����µ������ļ�(������Ŀ¼)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // ɾ����Ŀ¼
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��ȡ�ļ��޸�ʱ��
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getFileTime(String path) throws Exception {
		File file = new File(path);
		long modifiedTime = file.lastModified();
		Date date = new Date(modifiedTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
		String time = sdf.format(date);
		return time;
	}

	public static Map<String, Object> loadPPTInfo(String path) throws Exception {
		File file = new File(path);

		List<String> pptList = new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();

		String[] fileNames = file.list();
		for (String name : fileNames) {
			if (name.endsWith(".ppt") || name.endsWith(".pptx")) {
				pptList.add(name);
				timeList.add(getFileTime(path + name));
			}
		}
		map.put("pptList", pptList);
		map.put("timeList", timeList);
		return map;
	}

	/**
	 * ��ȡ�ļ��б�
	 * @param cursor
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getPPTList(Cursor cursor) throws Exception { 
		List<Object> list = new ArrayList<Object>();
		list.clear();
		String preCname = "";
		while (cursor.moveToNext()) {
			String cname = cursor.getString(2);
			if (!cname.equals(preCname)) {
				list.add(cname);
				preCname = cname;
			}
			// else {
			PPTFileInfo pptFileInfo = new PPTFileInfo();
			String tname = cursor.getString(1);
			String tpath = cursor.getString(0);
			pptFileInfo.setFilePath(tpath);
			pptFileInfo.setFileName(tname);
			pptFileInfo.setTime(getFileTime(tpath));
			list.add(pptFileInfo);
			// }
		}

		return list;
	}

	public static Map<String, List<PPTFileInfo>> mGetPPTList(Cursor cursor)
			throws Exception {
		Map<String, List<PPTFileInfo>> map = new HashMap<String, List<PPTFileInfo>>();

		String preCname = "";
		while (cursor.moveToNext()) {
			String cname = cursor.getString(2);
			if (!cname.equals(preCname)) {
				List<PPTFileInfo> list = new ArrayList<PPTFileInfo>();
				PPTFileInfo pptFileInfo = new PPTFileInfo();
				String tname = cursor.getString(1);
				String tpath = cursor.getString(0);
				pptFileInfo.setFilePath(tpath);
				pptFileInfo.setFileName(tname);
				pptFileInfo.setTime(getFileTime(tpath));
				list.add(pptFileInfo);
				map.put(cname, list);
				preCname = cname;
			} else {

				PPTFileInfo pptFileInfo = new PPTFileInfo();
				String tname = cursor.getString(1);
				String tpath = cursor.getString(0);
				pptFileInfo.setFilePath(tpath);
				pptFileInfo.setFileName(tname);
				pptFileInfo.setTime(getFileTime(tpath));
				map.get(cname).add(pptFileInfo);
			}
		}
		return map;
	}
}
