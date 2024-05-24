"use strict";
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const react_1 = __importDefault(require("react"));
const react_native_1 = require("react-native");
const NewAppScreen_1 = require("react-native/Libraries/NewAppScreen");
function Section({ children, title }) {
    const isDarkMode = (0, react_native_1.useColorScheme)() === 'dark';
    return (<react_native_1.View style={styles.sectionContainer}>
      <react_native_1.Text style={[
            styles.sectionTitle,
            {
                color: isDarkMode ? NewAppScreen_1.Colors.white : NewAppScreen_1.Colors.black,
            },
        ]}>
        {title}
      </react_native_1.Text>
      <react_native_1.Text style={[
            styles.sectionDescription,
            {
                color: isDarkMode ? NewAppScreen_1.Colors.light : NewAppScreen_1.Colors.dark,
            },
        ]}>
        {children}
      </react_native_1.Text>
    </react_native_1.View>);
}
function App() {
    const isDarkMode = (0, react_native_1.useColorScheme)() === 'dark';
    const backgroundStyle = {
        backgroundColor: isDarkMode ? NewAppScreen_1.Colors.darker : NewAppScreen_1.Colors.lighter,
    };
    return (<react_native_1.SafeAreaView style={backgroundStyle}>
      <react_native_1.StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} backgroundColor={backgroundStyle.backgroundColor}/>
      <react_native_1.ScrollView contentInsetAdjustmentBehavior="automatic" style={backgroundStyle}>
        <NewAppScreen_1.Header />
        <react_native_1.View style={{
            backgroundColor: isDarkMode ? NewAppScreen_1.Colors.black : NewAppScreen_1.Colors.white,
        }}>
          <Section title="Step One">
            Edit <react_native_1.Text style={styles.highlight}>App.tsx</react_native_1.Text> to change this
            screen and then come back to see your edits.
          </Section>
          <Section title="See Your Changes">
            <NewAppScreen_1.ReloadInstructions />
          </Section>
          <Section title="Debug">
            <NewAppScreen_1.DebugInstructions />
          </Section>
          <Section title="Learn More">
            Read the docs to discover what to do next:
          </Section>
          <NewAppScreen_1.LearnMoreLinks />
        </react_native_1.View>
      </react_native_1.ScrollView>
    </react_native_1.SafeAreaView>);
}
const styles = react_native_1.StyleSheet.create({
    sectionContainer: {
        marginTop: 32,
        paddingHorizontal: 24,
    },
    sectionTitle: {
        fontSize: 24,
        fontWeight: '600',
    },
    sectionDescription: {
        marginTop: 8,
        fontSize: 18,
        fontWeight: '400',
    },
    highlight: {
        fontWeight: '700',
    },
});
exports.default = App;
