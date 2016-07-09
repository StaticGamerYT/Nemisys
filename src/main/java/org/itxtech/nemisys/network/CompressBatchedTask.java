package org.itxtech.nemisys.network;

import org.itxtech.nemisys.Server;
import org.itxtech.nemisys.scheduler.AsyncTask;
import org.itxtech.nemisys.utils.Zlib;

import java.util.ArrayList;
import java.util.List;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class CompressBatchedTask extends AsyncTask {

    public int level = 7;
    public byte[] data;
    public byte[] finalData;
    public int channel = 0;
    public List<String> targets = new ArrayList<>();

    public CompressBatchedTask(byte[] data, List<String> targets) {
        this(data, targets, 7);
    }

    public CompressBatchedTask(byte[] data, List<String> targets, int level) {
        this(data, targets, level, 0);
    }

    public CompressBatchedTask(byte[] data, List<String> targets, int level, int channel) {
        this.data = data;
        this.targets = targets;
        this.level = level;
        this.channel = channel;
    }

    @Override
    public void onRun() {
        try {
            this.finalData = Zlib.deflate(this.data, this.level);
            this.data = null;
        } catch (Exception e) {
            //ignore
        }
    }

    @Override
    public void onCompletion(Server server) {
        server.broadcastPacketsCallback(this.finalData, this.targets);
    }
}