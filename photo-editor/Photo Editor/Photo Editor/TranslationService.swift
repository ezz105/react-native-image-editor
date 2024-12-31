@objc(TranslationService)
public final class TranslationService: NSObject {
    @objc public static let shared = TranslationService()

    private var translations: [String: String] = [:]

    private override init() {}

    @objc public func initTranslations(_ translations: [String: String]) {
        self.translations = translations
    }

    func getTranslation(for key: String) -> String? {
        let value = translations[key]
        return translations[key]
    }
}
