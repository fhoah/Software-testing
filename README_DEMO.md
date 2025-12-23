這個 demo 用於展示你的專案會輸出「五大功能」的摘要，方便交給老師作為證明。

使用方法（Windows cmd.exe）：
1. 開啟命令提示字元（cmd）並切換到專案根目錄。
2. 執行：
   run_policy_demo.bat

此腳本會：
- 編譯專案（跳過測試）
- 以多種配置執行 `App`（no args、weak、medium、strong、very_strong、以及自訂 minLength=10 requireSymbols=false）
- 將所有輸出合併在 `policy_demo.txt`，並在執行結束時印出內容

檔案：
- policy_demo.txt：交付物，包含每個執行的 `[POLICY]` 行（可直接交給老師）

如果需要我把 `policy_demo.txt` 轉成 PDF 或 ZIP，我也可以幫你做。

