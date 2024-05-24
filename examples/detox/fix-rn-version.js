"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = __importDefault(require("fs"));
const file = '../../android/build.gradle';
const replace = /implementation 'com.facebook.react:react-native:\+'/g;
const replacement = "implementation 'com.facebook.react:react-native:0.59.9'";
const encoding = 'utf8';
const data = fs_1.default.readFileSync(file, encoding);
const result = data.replace(replace, replacement);
fs_1.default.writeFileSync(file, result, encoding);
