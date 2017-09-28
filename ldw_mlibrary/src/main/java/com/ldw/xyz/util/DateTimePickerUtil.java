//package com.ldw.xyz.util;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import android.app.DatePickerDialog;
//import android.app.DatePickerDialog.OnDateSetListener;
//import android.app.TimePickerDialog;
//import android.app.TimePickerDialog.OnTimeSetListener;
//import android.content.Context;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//public class DateTimePickerUtil {
//
//	private Context mContext;
//	private TextView tvStartTime;
//	private TextView tvEndTime;
//	private TextView etReson;
//	private int years;
//	private int month;
//	private int day;
//	private int HH;
//	private int MM;
//	private DatePickerDialog datePickerDialog;
//	private TimePickerDialog timePickerDialog;
//	private String sliptStr;
//	private String colonStr;
//	private String blankStr;
//	private boolean isScancelable;
//	private int monthWidth;
//	// private DatePickerDialog mDialog;
//	private DatePicker dp;
//	private boolean isShowTime;
//	// 是否设置显示最小选择
//	private boolean minimualAble = false;
//
//	private String mInvokeMethod;
//
//	private boolean showDay = true;
//
//	// public DateTimePickerUtil(Context mContext,TextView tv1,TextView tv2) {
//	// // TODO Auto-generated constructor stub
//	// this.mContext = mContext;
//	// this.tvStartTime = tv1;
//	// this.tvEndTime = tv2;
//	// pickerDateTime(tvStartTime);
//	// addLinstener(tvStartTime);
//	// pickerDateTime(tvEndTime);
//	// addLinstener(tvEndTime);
//	//
//	// }
//
//	public DateTimePickerUtil(Context mContext, String slipStr,
//			String blankStr, boolean isScancelable, boolean isShowTime,
//			Date date, Object... textViews) {
//		// TODO Auto-generated constructor stub
//		this.mContext = mContext;
//		this.sliptStr = slipStr;
//		this.blankStr = blankStr;
//		this.isShowTime = isShowTime;
//		this.isScancelable = isScancelable;
//		// this.tvStartTime = textViews[0];
//		// this.tvEndTime = textViews[1];
//		for (int i = 0; i < textViews.length; i++) {
//			// Object o = textViews[i];
//			if (textViews[i] instanceof TextView) {
//				pickerDateTime((TextView) textViews[i], date);
//				addListener((TextView) textViews[i], date, isShowTime);
//			} else if (textViews[i] instanceof Button) {
//				pickerDateTime((Button) textViews[i], date);
//				addListener((Button) textViews[i], date, isShowTime);
//			} else if (textViews[i] instanceof EditText) {
//				pickerDateTime((EditText) textViews[i], date);
//				addListener((EditText) textViews[i], date, isShowTime);
//			}
//		}
//		// pickerDateTime(tvEndTime);
//		// addLinstener(tvEndTime);
//
//	}
//
//	/**
//	 *
//	 * @param mContext
//	 *            上下文
//	 * @param slipStr
//	 *            分隔符
//	 * @param blankStr
//	 *            间隔
//	 * @param isScancelable
//	 *            是否可取消
//	 * @param isShowTime
//	 *            是否显示时间选择器
//	 * @param date
//	 *            当前日期时间
//	 * @param imagview
//	 *            Imager控件
//	 * @param textViews
//	 *            TextView数组
//	 */
//	public DateTimePickerUtil(Context mContext, String slipStr,
//			String blankStr, View imagview, boolean isScancelable,
//			boolean isShowTime, Date date, Object... textViews) {
//		this.mContext = mContext;
//		this.sliptStr = slipStr;
//		this.blankStr = blankStr;
//		this.isScancelable = isScancelable;
//		this.isShowTime = isShowTime;
//		for (int i = 0; i < textViews.length; i++) {
//			if (textViews[i] instanceof TextView) {
//				pickerDateTime((TextView) textViews[i], date);
//				addListener2((TextView) textViews[i], imagview, date,
//						isShowTime);
//			}
//		}
//		LogUtil.i("DateTimePickerUtil", "---DateTimePickerUtil");
//	}
//
//	/**
//	 * 可设置是否显示日, 时间, 是否设置最小选择日期, 是否可取消的, 是否执行某一方法的
//	 *
//	 * @param mContext
//	 * @param slipStr
//	 *            分隔符
//	 * @param blankStr
//	 *            间隔
//	 * @param imagview
//	 * @param isScancelable
//	 *            是否可取消
//	 * @param isShowTime
//	 *            是否显示时间选择器
//	 * @param showDay
//	 *            是否显示日
//	 * @param minimualAble
//	 *            是否设置最小选择值
//	 * @param methodName
//	 *            执行的方法
//	 * @param date
//	 *            传入来的时间
//	 * @param textViews
//	 */
//	public DateTimePickerUtil(Context mContext, String slipStr,
//			String blankStr, View imagview, boolean isScancelable,
//			boolean isShowTime, boolean showDay, boolean minimualAble,
//			String methodName, Date date, Object... textViews) {
//		this.showDay = showDay;
//		this.minimualAble = minimualAble;
//		this.mInvokeMethod = methodName;
//		this.mContext = mContext;
//		this.sliptStr = slipStr;
//		this.blankStr = blankStr;
//		this.isScancelable = isScancelable;
//		this.isShowTime = isShowTime;
//		if (null != textViews) {
//			for (int i = 0; i < textViews.length; i++) {
//				if (textViews[i] instanceof TextView) {
//					pickerDateTime((TextView) textViews[i], date);
//					addListener2((TextView) textViews[i], imagview, date,
//							isShowTime);
//				}
//			}
//		} else {
//			pickerDateTime(null, date);
//			addListener2(null, imagview, date, isShowTime);
//		}
//		LogUtil.i("DateTimePickerUtil", "---DateTimePickerUtil");
//	}
//
//	/**
//	 *
//	 * @param mContext
//	 * @param slipStr
//	 * @param blankStr
//	 * @param imagview
//	 * @param isScancelable
//	 * @param isShowTime
//	 * @param showDay
//	 * @param minimualAble
//	 * @param isAfter
//	 *            是否加一天
//	 * @param methodName
//	 * @param date
//	 * @param textViews
//	 */
//	public DateTimePickerUtil(Context mContext, String slipStr,
//			String blankStr, View imagview, boolean isScancelable,
//			boolean isShowTime, boolean showDay, boolean minimualAble,
//			boolean isAfter, String methodName, Date date, Object... textViews) {
//		this.showDay = showDay;
//		this.minimualAble = minimualAble;
//		this.mInvokeMethod = methodName;
//		this.isAfter = isAfter;
//		this.mContext = mContext;
//		this.sliptStr = slipStr;
//		this.blankStr = blankStr;
//		this.isScancelable = isScancelable;
//		this.isShowTime = isShowTime;
//		if (null != textViews) {
//			for (int i = 0; i < textViews.length; i++) {
//				if (textViews[i] instanceof TextView) {
//					pickerDateTime((TextView) textViews[i], date);
//					addListener2((TextView) textViews[i], imagview, date,
//							isShowTime);
//				}
//			}
//		} else {
//			pickerDateTime(null, date);
//			addListener2(null, imagview, date, isShowTime);
//		}
//		LogUtil.i("DateTimePickerUtil", "---DateTimePickerUtil");
//	}
//
//	/**
//	 *
//	 * @param mContext
//	 *            上下文
//	 * @param slipStr
//	 *            分隔符
//	 * @param blankStr
//	 *            间隔
//	 * @param isScancelable
//	 *            是否可取消
//	 * @param isShowTime
//	 *            是否显示时间选择器
//	 * @param date
//	 *            当前日期时间
//	 * @param imagview
//	 *            Imager控件
//	 * @param methodName
//	 *            选择完后要执行的方法
//	 * @param textViews
//	 *            TextView数组
//	 */
//	public DateTimePickerUtil(Context mContext, String slipStr,
//			String blankStr, View imagview, boolean isScancelable,
//			boolean isShowTime, Date date, String methodName,
//			Object... textViews) {
//		this.mContext = mContext;
//		this.sliptStr = slipStr;
//		this.blankStr = blankStr;
//		this.isScancelable = isScancelable;
//		this.isShowTime = isShowTime;
//		mInvokeMethod = methodName;
//		for (int i = 0; i < textViews.length; i++) {
//			if (textViews[i] instanceof TextView) {
//				pickerDateTime((TextView) textViews[i], date);
//				addListener2((TextView) textViews[i], imagview, date,
//						isShowTime);
//			}
//		}
//		LogUtil.i("DateTimePickerUtil", "---DateTimePickerUtil");
//	}
//
//	/**
//	 * 只显示时间选择框
//	 *
//	 * @param mContext
//	 *            上下文
//	 * @param slipStr
//	 *            分隔符
//	 * @param isScancelable
//	 *            是否可取消
//	 * @param date
//	 *            当前日期时间
//	 * @param imagview
//	 *            View控件
//	 * @param textViews
//	 *            TextView数组
//	 */
//	public DateTimePickerUtil(Context mContext, String slipStr, View imagview,
//			boolean isScancelable, Date date, Object... textViews) {
//		this.mContext = mContext;
//		this.sliptStr = slipStr;
//		this.blankStr = blankStr;
//		this.isScancelable = isScancelable;
//		this.isShowTime = isShowTime;
//		for (int i = 0; i < textViews.length; i++) {
//			if (textViews[i] instanceof TextView) {
//				pickerTime((TextView) textViews[i], date);
//				addTimePickerListener((TextView) textViews[i], imagview, date);
//			}
//		}
////		LogUtil.i(this, "---DateTimePickerUtil");
//	}
//
//	private boolean isAfter;
//
//	public boolean isAfter() {
//		back isAfter;
//	}
//
//	public void setAfter(boolean isAfter) {
//		this.isAfter = isAfter;
//	}
//
//	private void pickerDateTime(TextView tv, Date date) {
//		// etStartTime.setInputType(type);
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		this.years = c.get(Calendar.YEAR);
//		this.month = c.get(Calendar.MONTH);
//		this.day = c.get(Calendar.DAY_OF_MONTH);
//		this.HH = c.get(Calendar.HOUR_OF_DAY);
//		this.MM = c.get(Calendar.MINUTE);
//		if (null != tv) {
//			String dateStr = "";
//			dateStr += years + sliptStr
//			// + "0"
//					+ formatTwoNum(month + 1);
//			if (showDay) {
//				if (isAfter) {
//					dateStr += sliptStr + formatTwoNum(day + 1);
//				} else {
//					dateStr += sliptStr + formatTwoNum(day);
//				}
//			}
//			dateStr += (isShowTime == false ? ("") : (" "
//					+ formatTwoNum(date.getHours()) + ":" + formatTwoNum(date
//					.getMinutes())));
//			tv.setText(dateStr);
//		}
//
//		// if (!StringHelper.isEmpty(mInvokeMethod)) {
//		// try {
//		// mContext.getClass().getMethod(mInvokeMethod, null)
//		// .invoke(mContext, null);
//		// } catch (IllegalArgumentException e) {
//		// e.printStackTrace();
//		// } catch (IllegalAccessException e) {
//		// e.printStackTrace();
//		// } catch (InvocationTargetException e) {
//		// e.printStackTrace();
//		// } catch (NoSuchMethodException e) {
//		// e.printStackTrace();
//		// }
//		// }
//	}
//
//	private void pickerTime(TextView tv, Date date) {
//		Calendar c = Calendar.getInstance();
//		this.HH = c.get(Calendar.HOUR_OF_DAY);
//		this.MM = c.get(Calendar.MINUTE);
//		c.setTime(date);
//		tv.setText((" " + formatTwoNum(date.getHours()) + ":" + formatTwoNum(date
//				.getMinutes())));
//	}
//
//	private String formatTwoNum(int month) {
//		if (month >= 10) {
//			// month = month + 1;
//			back "" + month;
//		} else {
//			// month = month + 1;
//			back "0" + month;
//		}
//	}
//
//	private void addListener2(final TextView tv, final View imageView,
//			final Date date, final boolean isShowTime) {
//		if (null != imageView) {
//			imageView.setOnClickListener(new OnClickListener() {
//				// 时间
//				@Override
//				public void onClick(View v) {
//					if (isShowTime) {
//						DateTimePickerUtil.this.timePickerDialog = new TimePickerDialog(
//								mContext,
//								TimePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,
//								new OnTimeSetListener() {
//
//									@Override
//									public void onTimeSet(TimePicker view,
//											int hourOfDay, int minute) {
//										DateTimePickerUtil.this.HH = hourOfDay;
//										DateTimePickerUtil.this.MM = minute;
//										if (null != tv) {
//											tv.setText(years + sliptStr
//													+ formatTwoNum(month + 1)
//													+ sliptStr
//													+ formatTwoNum(day)
//													+ blankStr
//													+ formatTwoNum(HH) + ":"
//													+ formatTwoNum(MM));
//										}
//									}
//								}, HH, MM, true);
//						DateTimePickerUtil.this.timePickerDialog
//								.setCancelable(isScancelable);
//						DateTimePickerUtil.this.timePickerDialog.show();
//					}
//					// 日期
//					DateTimePickerUtil.this.datePickerDialog = new DatePickerDialog(
//							mContext,
//							DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,
//							new OnDateSetListener() {
//								@Override
//								public void onDateSet(DatePicker view,
//										int year, int monthOfYear,
//										int dayOfMonth) {
//									// TODO Auto-generated method stub
//									DateTimePickerUtil.this.years = year;
//									DateTimePickerUtil.this.month = monthOfYear;
//									DateTimePickerUtil.this.day = dayOfMonth;
//									if (null != tv) {
//										tv.setText("");
//										String selectedDate = "";
//										selectedDate += years + sliptStr
//												+ formatTwoNum(month + 1);
//										if (showDay) {
//											selectedDate += sliptStr
//													+ formatTwoNum(day);
//										}
//										tv.setText(selectedDate);
//									}
//									if (!StringHelper.isEmpty(mInvokeMethod)) {
//										// try {
//										// mContext.getClass()
//										// .getMethod(mInvokeMethod, null)
//										// .invoke(mContext, null);
//										CommonUtils.invokeActivityMethod(
//												mContext, mInvokeMethod,
//												new Class[] { Integer.class,
//														Integer.class,
//														Integer.class }, year,
//												monthOfYear, dayOfMonth);
//										// } catch (IllegalArgumentException e)
//										// {
//										// e.printStackTrace();
//										// } catch (IllegalAccessException e) {
//										// e.printStackTrace();
//										// } catch (InvocationTargetException e)
//										// {
//										// e.printStackTrace();
//										// } catch (NoSuchMethodException e) {
//										// e.printStackTrace();
//										// }
//									}
//								}
//							}, years, month, day);
//					if (!showDay) {
//						((ViewGroup) ((ViewGroup) DateTimePickerUtil.this.datePickerDialog
//								.getDatePicker().getChildAt(0)).getChildAt(0))
//								.getChildAt(2).setVisibility(View.GONE);
//					}
//					DateTimePickerUtil.this.datePickerDialog
//							.setCancelable(isScancelable);
//					if (minimualAble) {
//						DateTimePickerUtil.this.datePickerDialog
//								.getDatePicker().setMinDate(date.getTime());
//					}
//					DateTimePickerUtil.this.datePickerDialog.show();
//				}
//			});
//		}
//	}
//
//	private void addTimePickerListener(final TextView tv, final View imageView,
//			final Date date) {
//		if (null != imageView) {
//			imageView.setOnClickListener(new OnClickListener() {
//				// 时间
//				@Override
//				public void onClick(View v) {
//					DateTimePickerUtil.this.timePickerDialog = new TimePickerDialog(
//							mContext,
//							TimePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,
//							new OnTimeSetListener() {
//								@Override
//								public void onTimeSet(TimePicker view,
//										int hourOfDay, int minute) {
//									DateTimePickerUtil.this.HH = hourOfDay;
//									DateTimePickerUtil.this.MM = minute;
//									tv.setText(formatTwoNum(HH) + ":"
//											+ formatTwoNum(MM));
//								}
//							}, HH, MM, true);
//					DateTimePickerUtil.this.timePickerDialog.show();
//					DateTimePickerUtil.this.timePickerDialog
//							.setCancelable(isScancelable);
//				}
//			});
//		}
//	}
//
//	private void addListener(final TextView tv, final Date date,
//			final boolean isShowTime) {
//		if (null != tv) {
//			tv.setOnClickListener(new OnClickListener() {
//
//				// 时间
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if (isShowTime) {
//						DateTimePickerUtil.this.timePickerDialog = new TimePickerDialog(
//								mContext,
//								TimePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,
//								new OnTimeSetListener() {
//
//									@Override
//									public void onTimeSet(TimePicker view,
//											int hourOfDay, int minute) {
//										// TODO Auto-generated method stub
//										DateTimePickerUtil.this.HH = hourOfDay;
//										DateTimePickerUtil.this.MM = minute;
//										tv.setText(years + sliptStr
//												+ formatTwoNum(month + 1)
//												+ sliptStr + formatTwoNum(day)
//												+ blankStr + formatTwoNum(HH)
//												+ ":" + formatTwoNum(MM));
//									}
//								}, HH, MM, true);
//						DateTimePickerUtil.this.timePickerDialog.show();
//						DateTimePickerUtil.this.timePickerDialog
//								.setCancelable(isScancelable);
//						// date(date, years, month, day);
//					}
//					// 日期
//					DateTimePickerUtil.this.datePickerDialog = new DatePickerDialog(
//							mContext,
//							DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,
//							new OnDateSetListener() {
//
//								@Override
//								public void onDateSet(DatePicker view,
//										int year, int monthOfYear,
//										int dayOfMonth) {
//									// TODO Auto-generated method stub
//									DateTimePickerUtil.this.years = year;
//									DateTimePickerUtil.this.month = monthOfYear;
//									DateTimePickerUtil.this.day = dayOfMonth;
//									tv.setText("");
//									tv.setText(years + sliptStr
//											+ formatTwoNum(month + 1)
//											+ sliptStr + formatTwoNum(day));
//									LogUtil.i("order", "month=" + month);
//								}
//							}, years, month, day);
//
//					DateTimePickerUtil.this.datePickerDialog.show();
//					DateTimePickerUtil.this.datePickerDialog
//							.setCancelable(isScancelable);
//					// dp = findDatePicker((ViewGroup)
//					// datePickerDialog.getWindow()
//					// .getDecorView());
//					// setPickerWidth(dp);
//				}
//			});
//		}
//	}
//
//	public void date(Date date, int y, int m, int d) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date begin = format.parse("1900-1-1");// 日历原始日期
//			Date start = format.parse(y + "-" + m + "-" + d);// 开始日期
//			Date end = format.parse(y + "-" + m + "-" + d);// 结束日期
//			Date curren = format.parse(format.format(date));// 当前日期
//			long endDays = (end.getTime() - begin.getTime())
//					/ (1000 * 60 * 60 * 24);
//			long startDays = (start.getTime() - begin.getTime())
//					/ (1000 * 60 * 60 * 24);
//			long currenDays = (curren.getTime() - begin.getTime())
//					/ (1000 * 60 * 60 * 24);
//			if (endDays < startDays) {
//				Toast.makeText(mContext, "结束日期不能小于开始日期", 4000).show();
//			}
//			if (currenDays > startDays) {
//				Toast.makeText(mContext, "开始日期不能小于当前日期", 4000).show();
//			}
//			if (currenDays > endDays) {
//				Toast.makeText(mContext, "结束日期不能小于当前日期", 4000).show();
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 从当前Dialog中查找DatePicker子控件
//	 *
//	 * @param group
//	 * @back
//	 */
//	private DatePicker findDatePicker(ViewGroup group) {
//		if (group != null) {
//			for (int i = 0, j = group.getChildCount(); i < j; i++) {
//				View child = group.getChildAt(i);
//				if (child instanceof DatePicker) {
//					back (DatePicker) child;
//				} else if (child instanceof ViewGroup) {
//					DatePicker result = findDatePicker((ViewGroup) child);
//					if (result != null)
//						back result;
//				}
//			}
//		}
//		back null;
//	}
//
//	private void setPickerWidth(DatePicker dp) {
//		Field[] fields = DatePicker.class.getDeclaredFields();
//		View v_month = null;
//		for (Field field : fields) {
//			field.setAccessible(true);
//			if (field.getType().getSimpleName().equals("NumberPicker")) {
//				try {
//					v_month = (View) field.get(dp);
//				} catch (Exception e) {
//
//				}
//			}
//		}
//		if (v_month != null) {
//			v_month.measure(0, 0);
//			v_month.getLayoutParams().width = (int) (v_month.getMeasuredWidth() * 1.5f);
//		}
//	}
//
//	public static String getSpecifiedDayBefore(String specifiedDay) {// 可以用new
//		// Date().toLocalString()传递参数
//		Calendar c = Calendar.getInstance();
//		Date date = null;
//		try {
//			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		c.setTime(date);
//		int day = c.get(Calendar.DATE);
//		c.set(Calendar.DATE, day - 1);
//
//		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
//				.getTime());
//		back dayBefore;
//	}
//
//	/**
//	 * 获得指定日期的后一天
//	 *
//	 * @param specifiedDay
//	 * @back
//	 */
//	public static String getSpecifiedDayAfter(String specifiedDay) {
//		Calendar c = Calendar.getInstance();
//		Date date = null;
//		try {
//			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		c.setTime(date);
//		int day = c.get(Calendar.DATE);
//		c.set(Calendar.DATE, day + 1);
//
//		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
//				.format(c.getTime());
//		back dayAfter;
//	}
//
//}
