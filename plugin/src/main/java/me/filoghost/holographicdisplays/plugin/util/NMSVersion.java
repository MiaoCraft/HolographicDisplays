/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.util;

import me.filoghost.fcommons.Preconditions;
import me.filoghost.fcommons.logging.ErrorCollector;
import me.filoghost.holographicdisplays.common.nms.NMSManager;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The NMS version is the name of the main package under net.minecraft.server, for example "v1_13_R2".
 */
public enum NMSVersion {

    // Not using shorter method reference syntax here because it initializes the class, causing a ClassNotFoundException
    v1_8_R2((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_8_R2.VersionNMSManager()),
    v1_8_R3((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_8_R3.VersionNMSManager()),
    v1_9_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_9_R1.VersionNMSManager()),
    v1_9_R2((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_9_R2.VersionNMSManager()),
    v1_10_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_10_R1.VersionNMSManager()),
    v1_11_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_11_R1.VersionNMSManager()),
    v1_12_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_12_R1.VersionNMSManager()),
    v1_13_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_13_R1.VersionNMSManager()),
    v1_13_R2((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_13_R2.VersionNMSManager()),
    v1_14_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_14_R1.VersionNMSManager()),
    v1_15_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_15_R1.VersionNMSManager()),
    v1_16_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_16_R1.VersionNMSManager()),
    v1_16_R2((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_16_R2.VersionNMSManager()),
    v1_16_R3((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_16_R3.VersionNMSManager()),
    v1_17_R1((errorCollector) -> new me.filoghost.holographicdisplays.nms.v1_17_R1.VersionNMSManager(errorCollector));

    private static final NMSVersion CURRENT_VERSION = extractCurrentVersion();

    private final NMSManagerFactory nmsManagerFactory;

    NMSVersion(NMSManagerFactory nmsManagerFactory) {
        this.nmsManagerFactory = nmsManagerFactory;
    }

    public NMSManager createNMSManager(ErrorCollector errorCollector) {
        return nmsManagerFactory.create(errorCollector);
    }

    public static NMSVersion getCurrent() {
        Preconditions.checkState(isValid(), "Current version is not valid");
        return CURRENT_VERSION;
    }

    public static boolean isValid() {
        return CURRENT_VERSION != null;
    }

    private static NMSVersion extractCurrentVersion() {
        Matcher matcher = Pattern.compile("v\\d+_\\d+_R\\d+").matcher(Bukkit.getServer().getClass().getPackage().getName());
        if (!matcher.find()) {
            return null;
        }

        String nmsVersionName = matcher.group();
        try {
            return valueOf(nmsVersionName);
        } catch (IllegalArgumentException e) {
            return null; // Unknown version
        }
    }


    private interface NMSManagerFactory {

        NMSManager create(ErrorCollector errorCollector);

    }

}
