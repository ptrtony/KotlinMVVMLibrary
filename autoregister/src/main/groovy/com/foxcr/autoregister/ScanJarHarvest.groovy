package com.foxcr.autoregister

class ScanJarHarvest {
    List<Harvest> harvestList = new ArrayList<>()
    class Harvest {
        String className
        String interfaceName
        boolean isInitClass
    }
}
