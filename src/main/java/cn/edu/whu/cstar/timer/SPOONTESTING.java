package cn.edu.whu.cstar.timer;

import java.util.List;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtAnonymousExecutable;
import spoon.reflect.visitor.filter.TypeFilter;

public class SPOONTESTING {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.addInputResource("src/main/resources/projs/Codec_parent/src/main/java/org/apache/commons/codec/net/URLCodec.java");
		launcher.getEnvironment().setCommentEnabled(true);
		CtModel metaModel = launcher.buildModel();
		List<CtAnonymousExecutable> lsAEXEs = metaModel.getElements(new TypeFilter(CtAnonymousExecutable.class));
		for(CtAnonymousExecutable aexe: lsAEXEs){
			System.out.println(">>> " + aexe.toString());
		}
		System.out.println("CtAnonymousExecutable");
	}

}
