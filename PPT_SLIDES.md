投影片大綱：Advanced Password Strength Analyzer — 五大服務展示

使用說明（直接複製到 PowerPoint 每張投影片的標題/內容與講稿）

Slide 1 — 標題
- 標題：Advanced Password Strength Analyzer & Security Suite
- 副標題：五大核心服務展示
- 講稿要點：簡短介紹專案目的（密碼強度分析、產生、字典檢查、歷史趨勢、批次處理）

Slide 2 — 專案概要（1 分鐘）
- 列點：
  - 技術：Java 17、Maven、JUnit5、JaCoCo、PMD
  - 目標：分析密碼強度、產生安全密碼、管理弱字典、歷史統計、批次處理
- 講稿：強調測試覆蓋與靜態分析的重要性

Slide 3 — 五大服務一覽（總覽）
- PasswordAnalyzerService：整合所有規則並產生 AnalysisResult（分數、熵、規則結果、強度等級）
- PasswordGeneratorService：支援長度、字元類別、發音式產生、保證每類至少一字元並打散
- DictionaryService：管理弱字典、載入檔案、Levenshtein 相似度、leetspeak 正規化
- HistoryService：儲存最近 50 個分析結果，計算平均、標準差、趨勢（IMPROVING/DECLINING/STABLE）
- BatchProcessingService：從檔案讀入密碼、分析，輸出 CSV 彙總
- 講稿：每個服務的責任與輸入/輸出

Slide 4 — 顯示每個服務的主要 API（可放程式碼片段）
- PasswordAnalyzerService
  - analyze(String password) -> AnalysisResult
- PasswordGeneratorService
  - generate(int length, boolean useUpper, boolean useLower, boolean useDigits, boolean useSymbols, boolean pronounceable)
- DictionaryService
  - loadFromFile(Path file), isWeak(String pw), isSimilar(String pw)
- HistoryService
  - push(AnalysisResult), getAverageScore(), getStdDev(), getTrend()
- BatchProcessingService
  - processFile(Path input, Path outputCsv)

Slide 5 — 實際操作 Demo（建議放三張截圖或三段 console 文字）
- 指令範例（可複製）：
  - mvn -DskipTests package
  - java -cp target\\classes com.example.security.App
  - java -cp target\\classes com.example.security.App strong
  - java -cp target\\classes com.example.security.App "minLength=10" "requireSymbols=false"
- 你可以截圖 Console 或把 `policy_demo.txt` 的對應段落貼到投影片
- 範例輸出（可貼）：
  - [POLICY] minLength=12, requireUpper=true, requireLower=true, requireDigits=true, requireSymbols=true
  - [POLICY] minLength=6, requireUpper=false, requireLower=true, requireDigits=false, requireSymbols=false
- 講稿：示範如何切換 preset、如何自訂 policy、說明輸出五個欄位就是你要展示給老師的內容

Slide 6 — 測試與質量保證（證明工程良好）
- 測試命令：mvn test
- JaCoCo 報表：target/site/jacoco/index.html（建議截圖覆蓋率 > 90%）
- PMD 報表：target/site/pmd.html（建議截圖警告/規則情況）
- 講稿：說明有超過 50 個單元測試、使用 JUnit5 + AssertJ、且使用 JaCoCo 檢查 branch coverage

Slide 7 — 交付物（老師可以要的）
- policy_demo.txt（演示輸出）
- target/security-suite-1.0-SNAPSHOT.jar（可執行 jar）
- target/site/jacoco/index.html（覆蓋率報表）
- target/site/pmd.html（靜態分析報表）
- run_policy_demo.bat（演示腳本）

Slide 8 — 線上/現場 Demo 流程（4 分鐘）
- 1 分鐘：專案目的與五大服務說明
- 2 分鐘：實機執行 run_policy_demo.bat 或三個 java 範例（展示 [POLICY] 行）
- 1 分鐘：展示 JaCoCo 報表與測試數據

Slide 9 — Q&A（準備幾個可能被問到的問題與答案）
- Q: 如何確保密碼產生器至少包含每個選擇的類別？
  - A: 產生時先為每個選類生成一個保證字符，剩餘長度隨機分配，最後 shuffle
- Q: 怎麼判定字典相似？
  - A: 使用 Levenshtein DP 算法並考慮 leetspeak normalization

Slide 10 — 結語與聯絡資訊
- 簡短重點總結
- 你的名字、學號、聯絡 email

-- END --

