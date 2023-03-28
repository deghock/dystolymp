package ru.spbu.distolymp.entity.enumeration;

/**
 * @author Vladislav Konovalov
 */
public enum Accessible {
    yes{
        public String toString(){
            return "yes";
        }
        public String getName(){
            return "yes";
        }
    }, no
}