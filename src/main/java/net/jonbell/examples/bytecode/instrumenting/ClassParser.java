package net.jonbell.examples.bytecode.instrumenting;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassParser extends ClassVisitor {

	public ClassParser(ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
	}

	public static final String CLASS_COVERAGE_FIELD = "__jonBellExampleClassCovered";
	private String className;
	private boolean isClass;
	private boolean fixLdcClass;
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		this.className = name;
		this.fixLdcClass = (version & 0xFFFF) < Opcodes.V1_5;
		this.isClass = (access & Opcodes.ACC_INTERFACE) == 0 && (access & Opcodes.ACC_ENUM) == 0;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
		//System.out.println(name + desc);
		return new MethodVisitor(Opcodes.ASM5, mv) {
			@Override
			public void visitInsn(int opcode){
				super.visitInsn(opcode); 
				//System.out.println("opcode:" + opcode);
				Instrumenter.classWriters[Instrumenter.classId].println("opcode:" + opcode);
			}
			@Override
			public void visitVarInsn(int opcode, int var){
				super.visitVarInsn(opcode, var);
				//System.out.println("opcode:" + opcode + ", var:" + var);
				Instrumenter.classWriters[Instrumenter.classId].println("opcode:" + opcode + ", var:" + var);
			}
			
			@Override
			public void visitIntInsn(int opcode, int operand){
				super.visitIntInsn(opcode, operand);
				//System.out.println("opcode:" + opcode + ", operand:" + operand);
				Instrumenter.classWriters[Instrumenter.classId].println("opcode:" + opcode + ", operand:" + operand);
			}
			
			@Override
			public void visitFieldInsn(int opcode, String owner, String name, String desc){
				super.visitFieldInsn(opcode, owner, name, desc);
				//System.out.println("opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", desc:" + desc);
				Instrumenter.classWriters[Instrumenter.classId].println("opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", desc:" + desc);
			}
			
			@Override
			public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf){
				super.visitMethodInsn(opcode, owner, name, desc, itf);
				//System.out.println("opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", desc:" + desc);
				Instrumenter.classWriters[Instrumenter.classId].println("opcode:" + opcode + ", owner:" + owner + ", name:" + name + ", desc:" + desc);
			}
			
			@Override
			public void visitLdcInsn(Object cst){
				super.visitLdcInsn(cst);
				if (cst instanceof String) {
					//System.out.println("opcode:18, " + (String)cst);
					Instrumenter.classWriters[Instrumenter.classId].println("opcode:18, " + (String)cst);
				}
			}
			@Override
			public void visitLineNumber(int line, Label start){
				super.visitLineNumber(line, start);
				//System.out.println("line: " + line);
				Instrumenter.classWriters[Instrumenter.classId].println("line: " + line);
			}
			
			@Override
			public void visitCode() {
				super.visitCode();
			}
		};
	}
	
	@Override
	public void visitEnd() {
		super.visitEnd();
	}
}
