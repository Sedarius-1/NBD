package org.cassandra;

public class Consts {

    public static final String defaultKeyspace = "gun_shop";
    public static final String clientTable = "client";
    public static final String weaponTable = "weapon";
    public static final String purchaseTable = "purchase";
    public static final String id = "id";

    public static final String clientTableId = clientTable+id;
    public static final String weaponTableId = weaponTable+id;
    public static final String purchaseTableId = purchaseTable+id;
    public static final String purchaseTableByClient = "purchasebyclient";
    public static final String purchaseTableByWeapon = "purchasebyweapon";

    public static final String writeConsistencyLevel = "TWO";
    public static final String readConsistencyLevel = "ONE";
}
