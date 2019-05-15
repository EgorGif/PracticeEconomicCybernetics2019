#define _CRT_SECURE_NO_WARNINGS
#include <jni.h>
#include <windows.h>
#include <lzexpand.h>
#pragma comment(lib, "Lz32.lib")
#include <iostream>
#include <cstring>
#include "LZFunctions.h"

OFSTRUCT source;
OFSTRUCT destination;

JNIEXPORT jint JNICALL Java_LZFunctions_lzOpenSourceFile(JNIEnv *env, jobject obj, jstring str) {
    const char *path = env->GetStringUTFChars(str, NULL);
	int size = env->GetStringUTFLength(str);
	char pathToFile[256];
	strncpy(pathToFile, path, size);
	pathToFile[size] = '\0';
    INT hSrc = LZOpenFile(pathToFile, &source, OF_READ);
    if (hSrc == LZERROR_BADINHANDLE || hSrc == LZERROR_GLOBALLOC) {
        std::cout << "Error in lzOpenSourceFile" << std::endl;
    }
    std::cout << "Compressed (source) file was opened" << std::endl;
    return hSrc;
}

JNIEXPORT jint JNICALL Java_LZFunctions_lzOpenDestinationFile(JNIEnv *env, jobject obj, jstring str) {
    const char *path = env->GetStringUTFChars(str, NULL);
	int size = env->GetStringUTFLength(str);
	char pathToFile[256];
	strncpy(pathToFile, path, size);
	pathToFile[size] = '\0';
    INT hDest = LZOpenFile(pathToFile, &destination, OF_CREATE | OF_WRITE);
    if (hDest == LZERROR_BADINHANDLE || hDest == LZERROR_GLOBALLOC) {
        std::cout << "Error in lzOpenDestinationFile" << std::endl;
    }
    std::cout << "Destination (decompressed) file was opened" << std::endl;
    return hDest;
}

JNIEXPORT void JNICALL Java_LZFunctions_lzCopyData(JNIEnv *env, jobject obj, jint src, jint dest) {
    INT sizeOfDestinationFile = LZCopy(src, dest);
    if (sizeOfDestinationFile == LZERROR_BADINHANDLE ||
        sizeOfDestinationFile == LZERROR_BADOUTHANDLE ||
        sizeOfDestinationFile == LZERROR_GLOBALLOC ||
        sizeOfDestinationFile == LZERROR_GLOBLOCK ||
        sizeOfDestinationFile == LZERROR_READ) {
        std::cout << "Error in lzCopyData" << std::endl;
    }
    std::cout << "Data was copied, "
              << "destination file was created, size: "
              << sizeOfDestinationFile << std::endl;
}

JNIEXPORT void JNICALL Java_LZFunctions_lzCloseFile(JNIEnv *env, jobject obj, jint h) {
    LZClose(h);
    std::cout << "Specified file was closed" << std::endl;
}
