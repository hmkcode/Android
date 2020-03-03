package simple.plusmortproject;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelper;
import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;

public class Repeller extends BackupAgentHelper {
    public Repeller() {
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {
        super.onBackup(oldState, data, newState);
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        super.onRestore(data, appVersionCode, newState);
    }

    @Override
    public void addHelper(String keyPrefix, BackupHelper helper) {
        super.addHelper(keyPrefix, helper);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFullBackup(FullBackupDataOutput data) throws IOException {
        super.onFullBackup(data);
    }

    @Override
    public void onRestoreFile(ParcelFileDescriptor data, long size, File destination, int type, long mode, long mtime) throws IOException {
        super.onRestoreFile(data, size, destination, type, mode, mtime);
    }

    @Override
    public void onRestoreFinished() {
        super.onRestoreFinished();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public PackageManager getPackageManager() {
        return super.getPackageManager();
    }
}
