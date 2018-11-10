package me.shouheng.libraries.serial;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivitySerializeBinding;
import timber.log.Timber;

/**
 * @author shouh
 * @version $Id: SerializeActivity, v 0.1 2018/10/21 17:58 shouh Exp$
 */
@Route(path = BaseConstants.LIBRARY_SERIAL)
public class SerializeActivity extends CommonActivity<ActivitySerializeBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_serialize;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        /*----------------------------------Serializable-------------------------------------*/
        SerializeUtils.setStudent(new Student("Student.01",
                12,
                new Role("Role01"),
                () -> "do whatever"));
        Student student = SerializeUtils.getStudent();
        assert student != null;
        String strStu = student.toString() + "\n\n" + student.i.doSomething();
        getBinding().tvStudent.setText(strStu);

        /*----------------------------------------Parcelable--------------------------------------------*/
        Monster monster = getIntent().getParcelableExtra(BaseConstants.LIBRARY_SERIAL_ARG_MONSTER);
        getBinding().tvMonster.setText(monster.toString());
    }

    public static class Student implements Serializable {
        final String name;
        final int age;

        /**
         * 内部的字段也应该实现序列化接口，才能进行正确序列化
         */
        final Role role;

        /**
         * 接口也必须实现 Serializable 接口
         */
        final IInterface i;

        public Student(String name, int age, Role role, IInterface i) {
            this.name = name;
            this.age = age;
            this.role = role;
            this.i = i;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", role=" + role +
                    '}';
        }
    }

    public static class Role implements Serializable {

        final String name;

        public Role(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Role{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public interface IInterface extends Serializable {
        String doSomething();
    }

    // region 直接使用接口的实现方式
    /*

    public static class Monster implements Parcelable {
        final String name;
        final int age;
        final Weapon weapon;
        final Date birthday;

        public Monster(String name, int age, Weapon weapon, Date birthday) {
            this.name = name;
            this.age = age;
            this.weapon = weapon;
            this.birthday = birthday;
        }

        Monster(Parcel in) {
            name = in.readString();
            age = in.readInt();
            weapon = in.readParcelable(Weapon.class.getClassLoader());
            birthday = (Date) in.readSerializable();
        }

        public static final Creator<Monster> CREATOR = new Creator<Monster>() {
            @Override
            public Monster createFromParcel(Parcel in) {
                return new Monster(in);
            }

            @Override
            public Monster[] newArray(int size) {
                return new Monster[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(age);
            dest.writeParcelable(weapon, 0);
            dest.writeSerializable(birthday);
        }

        @Override
        public String toString() {
            return "Monster{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", weapon=" + weapon +
                    ", birthday=" + birthday +
                    '}';
        }
    }

    public static class Weapon implements Parcelable {
        final String name;

        public Weapon(String name) {
            this.name = name;
        }

        Weapon(Parcel in) {
            name = in.readString();
        }

        public static final Creator<Weapon> CREATOR = new Creator<Weapon>() {
            @Override
            public Weapon createFromParcel(Parcel in) {
                return new Weapon(in);
            }

            @Override
            public Weapon[] newArray(int size) {
                return new Weapon[size];
            }
        };

        @Override
        public String toString() {
            return "Weapon{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
        }
    }

    */
    // endregion

    public static class Monster extends BaseParcelable<Monster> {

        public static final Creator<Monster> CREATOR = CreatorFactory.getCreator(Monster.class);

        String name;
        int age;
        Weapon weapon;
        Date birthday;
        Grade grade;

        public Monster(String name, int age, Weapon weapon, Date birthday, Grade grade) {
            this.name = name;
            this.age = age;
            this.weapon = weapon;
            this.birthday = birthday;
            this.grade = grade;
        }

        public Monster(Parcel in) {
            super(in);
        }

        @Override
        public String toString() {
            return "Monster{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", weapon=" + weapon +
                    ", birthday=" + birthday +
                    ", grade=" + grade +
                    "}";
        }
    }

    public static class Weapon extends BaseParcelable {

        public static final Creator<Weapon> CREATOR = CreatorFactory.getCreator(Weapon.class);

        String name;

        public Weapon(String name) {
            this.name = name;
        }

        /**
         * 需要时 public 的，并且字段不能是 final 的
         *
         * @param in
         */
        public Weapon(Parcel in) {
            super(in);
        }

        @Override
        public String toString() {
            return "Weapon{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static class Grade {
        int grade;

        public Grade(int grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Grade{" +
                    "grade=" + grade +
                    '}';
        }
    }

    // TODO There are many available methods for simplifying the usage of Parcelable, most are implemented by auto-generating codes
    public static abstract class BaseParcelable<T> implements Parcelable {

        protected BaseParcelable() { }

        protected BaseParcelable(Parcel in) {
            // TODO 父类中的字段
            Field[] fields = getClass().getDeclaredFields();
            Class<?> fieldType;
            CreatorFactory.TypeHandler<?> typeHandler;
            try {
                for (Field field : fields) {
                    fieldType = field.getType();
                    /*------------------------------使用类型处理器---------------------------------*/
                    if ((typeHandler = CreatorFactory.handlerMap.get(fieldType)) != null) {
                        field.set(this, typeHandler.read(in));
                        continue;
                    }
                    /*------------------------------默认数据类型--------------------------------*/
                    if (fieldType == String.class) {
                        field.set(this, in.readString());
                    } else if (fieldType == byte.class || fieldType == Byte.class) {
                        field.set(this, in.readByte());
                    } else if (fieldType == short.class || fieldType == Short.class) {
                        field.set(this, in.readInt());
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        field.set(this, in.readInt());
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        field.set(this, in.readLong());
                    } else if (fieldType == float.class || fieldType == Float.class) {
                        field.set(this, in.readFloat());
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        field.set(this, in.readDouble());
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        field.set(this, in.readInt() == 1);
                    } else if (Serializable.class.isAssignableFrom(fieldType)) {
                        field.set(this, in.readSerializable());
                    } else if (Parcelable.class.isAssignableFrom(fieldType)) {
                        field.set(this, in.readParcelable(fieldType.getClassLoader()));
                    }
                }
            } catch (IllegalAccessException e) {
                Timber.e(e);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            Field[] fields = getClass().getDeclaredFields();
            Class<?> fieldType;
            CreatorFactory.TypeHandler<Object> typeHandler;
            try {
                for (Field field : fields) {
                    fieldType = field.getType();
                    /*------------------------------使用类型处理器---------------------------------*/
                    if ((typeHandler = (CreatorFactory.TypeHandler<Object>) CreatorFactory.handlerMap.get(fieldType)) != null) {
                        typeHandler.write(dest, field.get(this));
                        continue;
                    }
                    /*------------------------------默认数据类型--------------------------------*/
                    if (fieldType == String.class) {
                        dest.writeString((String) field.get(this));
                    } else if (fieldType == byte.class || fieldType == Byte.class) {
                        dest.writeByte((Byte) field.get(this));
                    } else if (fieldType == short.class || fieldType == Short.class) {
                        dest.writeInt((Short) field.get(this));
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        dest.writeInt((Integer) field.get(this));
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        dest.writeLong((Long) field.get(this));
                    } else if (fieldType == float.class || fieldType == Float.class) {
                        dest.writeFloat((Float) field.get(this));
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        dest.writeDouble((Double) field.get(this));
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        dest.writeInt((Boolean) field.get(this) ? 1 : 0);
                    } else if (Serializable.class.isAssignableFrom(fieldType)) {
                        dest.writeSerializable((Serializable) field.get(this));
                    } else if (Parcelable.class.isAssignableFrom(fieldType)) {
                        dest.writeParcelable((Parcelable) field.get(this), 0);
                    }
                }
            } catch (IllegalAccessException e) {
                Timber.e(e);
            }
        }
    }

    public static class CreatorFactory {

        public static Map<Class<?>, TypeHandler<?>> handlerMap = new HashMap<>();

        /*-----------------------自定义的类型处理器在这里添加进去------------------------*/
        static {
            addTypeHandler(Date.class, new TypeHandler<Date>() {
                @Override
                public void write(Parcel in, Date date) {
                    in.writeLong(date.getTime());
                }

                @Override
                public Date read(Parcel in) {
                    return new Date(in.readLong());
                }
            });
            addTypeHandler(Grade.class, new TypeHandler<Grade>() {
                @Override
                public void write(Parcel in, Grade grade) {
                    in.writeInt(grade.grade);
                }

                @Override
                public Grade read(Parcel in) {
                    return new Grade(in.readInt());
                }
            });
        }

        /**
         * 获取指定类型的创建器
         *
         * @param type 类型
         * @param <T> 类型
         * @return 创建器
         */
        public static <T extends BaseParcelable> Parcelable.Creator<T> getCreator(Class<T> type) {
            return new Parcelable.Creator<T>() {
                @Override
                public T createFromParcel(Parcel in) {
                    try {
                        Constructor<T> c = type.getConstructor(Parcel.class);
                        return c.newInstance(in);
                    } catch (IllegalAccessException e) {
                        Timber.e(e);
                    } catch (InstantiationException e) {
                        Timber.e(e);
                    } catch (NoSuchMethodException e) {
                        Timber.e(e);
                    } catch (InvocationTargetException e) {
                        Timber.e(e);
                    }
                    throw new RuntimeException("illegal");
                }

                @Override
                public T[] newArray(int size) {
                    return (T[]) Array.newInstance(type, size);
                }
            };
        }

        /**
         * 类型处理器
         *
         * @param <T>
         */
        public interface TypeHandler<T> {
            void write(Parcel in, T t);
            T read(Parcel in);
        }

        /**
         * 添加指定类型的类型处理器
         *
         * @param type 类型
         * @param typeHandler 类型处理器
         * @param <T> 类型
         */
        public static <T> void addTypeHandler(Class<T> type, TypeHandler<T> typeHandler) {
            handlerMap.put(type, typeHandler);
        }
    }
}
