/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\ofaro\\AndroidStudioProjects\\AildFinalProject\\app\\src\\main\\aidl\\com\\nanofaroque\\capstone\\aidl\\WeatherResults.aidl
 */
package com.nanofaroque.capstone.aidl;
/**
 * Interface defining the method that receives callbacks from the
 * WeatherServiceAsync.  This method should be implemented by the
 * WeatherActivity.
 */
public interface WeatherResults extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.nanofaroque.capstone.aidl.WeatherResults
{
private static final java.lang.String DESCRIPTOR = "com.nanofaroque.capstone.aidl.WeatherResults";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.nanofaroque.capstone.aidl.WeatherResults interface,
 * generating a proxy if needed.
 */
public static com.nanofaroque.capstone.aidl.WeatherResults asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.nanofaroque.capstone.aidl.WeatherResults))) {
return ((com.nanofaroque.capstone.aidl.WeatherResults)iin);
}
return new com.nanofaroque.capstone.aidl.WeatherResults.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendResults:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.nanofaroque.capstone.aidl.WeatherData> _arg0;
_arg0 = data.createTypedArrayList(com.nanofaroque.capstone.aidl.WeatherData.CREATOR);
this.sendResults(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.nanofaroque.capstone.aidl.WeatherResults
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * This one-way (non-blocking) method allows WeatherServiceAsync
     * to return the List of WeatherData results associated with a
     * one-way WeatherRequest.getCurrentWeather() call.
     */
@Override public void sendResults(java.util.List<com.nanofaroque.capstone.aidl.WeatherData> results) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(results);
mRemote.transact(Stub.TRANSACTION_sendResults, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_sendResults = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * This one-way (non-blocking) method allows WeatherServiceAsync
     * to return the List of WeatherData results associated with a
     * one-way WeatherRequest.getCurrentWeather() call.
     */
public void sendResults(java.util.List<com.nanofaroque.capstone.aidl.WeatherData> results) throws android.os.RemoteException;
}
