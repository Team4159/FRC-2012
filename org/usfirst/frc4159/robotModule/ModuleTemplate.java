package org.usfirst.frc4159.robotModule;

public class ModuleTemplate extends Module {

	private static ModuleTemplate instance;
	public static synchronized ModuleTemplate getInstance(){
		if(instance == null)
			instance = new ModuleTemplate();
		return instance;
	}
}
