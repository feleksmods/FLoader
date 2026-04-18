package me.felek.floader.injection.injs;

import javassist.*;
import javassist.expr.ExprEditor;
import me.felek.floader.injection.Injected;

public class GameCalendarInject implements Injected {
    @Override
    public void inject(ClassPool pool, String clname) throws NotFoundException, CannotCompileException {
        CtClass cc = pool.get(clname);

        CtMethod mNextDays = cc.getDeclaredMethod("nextDays");
        mNextDays.insertBefore("{ me.felek.floader.lua.eventSystem.EventBus.call(\"onDayChange\", ($w)$1); }");

        mNextDays.instrument(new ExprEditor() {
            public void edit(javassist.expr.FieldAccess f) throws javassist.CannotCompileException {
                if (f.getFieldName().equals("currMonth") && f.isWriter()) {
                    f.replace("{ $proceed($$); me.felek.floader.lua.eventSystem.EventBus.call(\"onMonthChange\", ($w)age.of.civilizations2.jakowski.lukasz.GameCalendar.currMonth); }");
                }
            }
        });
    }
}
